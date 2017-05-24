package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PersonEthnicity;
import gov.ca.cwds.cals.rest.api.repository.PersonEthnicityRepository;
import gov.ca.cwds.cals.rest.api.service.dto.PersonEthnicityDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonEthnicityMapper;
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
 * Test class for the PersonEthnicityResource REST controller.
 *
 * @see PersonEthnicityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PersonEthnicityResourceIntTest {

    @Autowired
    private PersonEthnicityRepository personEthnicityRepository;

    @Autowired
    private PersonEthnicityMapper personEthnicityMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonEthnicityMockMvc;

    private PersonEthnicity personEthnicity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonEthnicityResource personEthnicityResource = new PersonEthnicityResource(personEthnicityRepository, personEthnicityMapper);
        this.restPersonEthnicityMockMvc = MockMvcBuilders.standaloneSetup(personEthnicityResource)
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
    public static PersonEthnicity createEntity(EntityManager em) {
        PersonEthnicity personEthnicity = new PersonEthnicity();
        return personEthnicity;
    }

    @Before
    public void initTest() {
        personEthnicity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonEthnicity() throws Exception {
        int databaseSizeBeforeCreate = personEthnicityRepository.findAll().size();

        // Create the PersonEthnicity
        PersonEthnicityDTO personEthnicityDTO = personEthnicityMapper.personEthnicityToPersonEthnicityDTO(personEthnicity);
        restPersonEthnicityMockMvc.perform(post("/api/person-ethnicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEthnicityDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonEthnicity in the database
        List<PersonEthnicity> personEthnicityList = personEthnicityRepository.findAll();
        assertThat(personEthnicityList).hasSize(databaseSizeBeforeCreate + 1);
        PersonEthnicity testPersonEthnicity = personEthnicityList.get(personEthnicityList.size() - 1);
    }

    @Test
    @Transactional
    public void createPersonEthnicityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personEthnicityRepository.findAll().size();

        // Create the PersonEthnicity with an existing ID
        personEthnicity.setId(1L);
        PersonEthnicityDTO personEthnicityDTO = personEthnicityMapper.personEthnicityToPersonEthnicityDTO(personEthnicity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonEthnicityMockMvc.perform(post("/api/person-ethnicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEthnicityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonEthnicity> personEthnicityList = personEthnicityRepository.findAll();
        assertThat(personEthnicityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonEthnicities() throws Exception {
        // Initialize the database
        personEthnicityRepository.saveAndFlush(personEthnicity);

        // Get all the personEthnicityList
        restPersonEthnicityMockMvc.perform(get("/api/person-ethnicities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEthnicity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPersonEthnicity() throws Exception {
        // Initialize the database
        personEthnicityRepository.saveAndFlush(personEthnicity);

        // Get the personEthnicity
        restPersonEthnicityMockMvc.perform(get("/api/person-ethnicities/{id}", personEthnicity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personEthnicity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonEthnicity() throws Exception {
        // Get the personEthnicity
        restPersonEthnicityMockMvc.perform(get("/api/person-ethnicities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonEthnicity() throws Exception {
        // Initialize the database
        personEthnicityRepository.saveAndFlush(personEthnicity);
        int databaseSizeBeforeUpdate = personEthnicityRepository.findAll().size();

        // Update the personEthnicity
        PersonEthnicity updatedPersonEthnicity = personEthnicityRepository.findOne(personEthnicity.getId());
        PersonEthnicityDTO personEthnicityDTO = personEthnicityMapper.personEthnicityToPersonEthnicityDTO(updatedPersonEthnicity);

        restPersonEthnicityMockMvc.perform(put("/api/person-ethnicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEthnicityDTO)))
            .andExpect(status().isOk());

        // Validate the PersonEthnicity in the database
        List<PersonEthnicity> personEthnicityList = personEthnicityRepository.findAll();
        assertThat(personEthnicityList).hasSize(databaseSizeBeforeUpdate);
        PersonEthnicity testPersonEthnicity = personEthnicityList.get(personEthnicityList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonEthnicity() throws Exception {
        int databaseSizeBeforeUpdate = personEthnicityRepository.findAll().size();

        // Create the PersonEthnicity
        PersonEthnicityDTO personEthnicityDTO = personEthnicityMapper.personEthnicityToPersonEthnicityDTO(personEthnicity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonEthnicityMockMvc.perform(put("/api/person-ethnicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEthnicityDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonEthnicity in the database
        List<PersonEthnicity> personEthnicityList = personEthnicityRepository.findAll();
        assertThat(personEthnicityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonEthnicity() throws Exception {
        // Initialize the database
        personEthnicityRepository.saveAndFlush(personEthnicity);
        int databaseSizeBeforeDelete = personEthnicityRepository.findAll().size();

        // Get the personEthnicity
        restPersonEthnicityMockMvc.perform(delete("/api/person-ethnicities/{id}", personEthnicity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonEthnicity> personEthnicityList = personEthnicityRepository.findAll();
        assertThat(personEthnicityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonEthnicity.class);
    }
}
