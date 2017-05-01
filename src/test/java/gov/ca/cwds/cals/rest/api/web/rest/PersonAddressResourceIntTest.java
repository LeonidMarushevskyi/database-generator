package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PersonAddress;
import gov.ca.cwds.cals.rest.api.repository.PersonAddressRepository;
import gov.ca.cwds.cals.rest.api.service.dto.PersonAddressDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonAddressMapper;
import gov.ca.cwds.cals.rest.api.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonAddressResource REST controller.
 *
 * @see PersonAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PersonAddressResourceIntTest {

    @Autowired
    private PersonAddressRepository personAddressRepository;

    @Autowired
    private PersonAddressMapper personAddressMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonAddressMockMvc;

    private PersonAddress personAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonAddressResource personAddressResource = new PersonAddressResource(personAddressRepository, personAddressMapper);
        this.restPersonAddressMockMvc = MockMvcBuilders.standaloneSetup(personAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonAddress createEntity(EntityManager em) {
        PersonAddress personAddress = new PersonAddress();
        return personAddress;
    }

    @Before
    public void initTest() {
        personAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonAddress() throws Exception {
        int databaseSizeBeforeCreate = personAddressRepository.findAll().size();

        // Create the PersonAddress
        PersonAddressDTO personAddressDTO = personAddressMapper.personAddressToPersonAddressDTO(personAddress);
        restPersonAddressMockMvc.perform(post("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonAddress in the database
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeCreate + 1);
        PersonAddress testPersonAddress = personAddressList.get(personAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void createPersonAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personAddressRepository.findAll().size();

        // Create the PersonAddress with an existing ID
        personAddress.setId(1L);
        PersonAddressDTO personAddressDTO = personAddressMapper.personAddressToPersonAddressDTO(personAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonAddressMockMvc.perform(post("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonAddresses() throws Exception {
        // Initialize the database
        personAddressRepository.saveAndFlush(personAddress);

        // Get all the personAddressList
        restPersonAddressMockMvc.perform(get("/api/person-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personAddress.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPersonAddress() throws Exception {
        // Initialize the database
        personAddressRepository.saveAndFlush(personAddress);

        // Get the personAddress
        restPersonAddressMockMvc.perform(get("/api/person-addresses/{id}", personAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personAddress.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonAddress() throws Exception {
        // Get the personAddress
        restPersonAddressMockMvc.perform(get("/api/person-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonAddress() throws Exception {
        // Initialize the database
        personAddressRepository.saveAndFlush(personAddress);
        int databaseSizeBeforeUpdate = personAddressRepository.findAll().size();

        // Update the personAddress
        PersonAddress updatedPersonAddress = personAddressRepository.findOne(personAddress.getId());
        PersonAddressDTO personAddressDTO = personAddressMapper.personAddressToPersonAddressDTO(updatedPersonAddress);

        restPersonAddressMockMvc.perform(put("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddressDTO)))
            .andExpect(status().isOk());

        // Validate the PersonAddress in the database
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeUpdate);
        PersonAddress testPersonAddress = personAddressList.get(personAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonAddress() throws Exception {
        int databaseSizeBeforeUpdate = personAddressRepository.findAll().size();

        // Create the PersonAddress
        PersonAddressDTO personAddressDTO = personAddressMapper.personAddressToPersonAddressDTO(personAddress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonAddressMockMvc.perform(put("/api/person-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonAddress in the database
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonAddress() throws Exception {
        // Initialize the database
        personAddressRepository.saveAndFlush(personAddress);
        int databaseSizeBeforeDelete = personAddressRepository.findAll().size();

        // Get the personAddress
        restPersonAddressMockMvc.perform(delete("/api/person-addresses/{id}", personAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonAddress> personAddressList = personAddressRepository.findAll();
        assertThat(personAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonAddress.class);
    }
}
