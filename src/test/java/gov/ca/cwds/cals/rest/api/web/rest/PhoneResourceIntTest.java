package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.Phone;
import gov.ca.cwds.cals.rest.api.repository.PhoneRepository;
import gov.ca.cwds.cals.rest.api.service.dto.PhoneDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PhoneMapper;
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
 * Test class for the PhoneResource REST controller.
 *
 * @see PhoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PhoneResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPhoneMockMvc;

    private Phone phone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhoneResource phoneResource = new PhoneResource(phoneRepository, phoneMapper);
        this.restPhoneMockMvc = MockMvcBuilders.standaloneSetup(phoneResource)
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
    public static Phone createEntity(EntityManager em) {
        Phone phone = new Phone()
            .number(DEFAULT_NUMBER);
        return phone;
    }

    @Before
    public void initTest() {
        phone = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhone() throws Exception {
        int databaseSizeBeforeCreate = phoneRepository.findAll().size();

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);
        restPhoneMockMvc.perform(post("/api/phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeCreate + 1);
        Phone testPhone = phoneList.get(phoneList.size() - 1);
        assertThat(testPhone.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneRepository.findAll().size();

        // Create the Phone with an existing ID
        phone.setId(1L);
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneMockMvc.perform(post("/api/phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneRepository.findAll().size();
        // set the field null
        phone.setNumber(null);

        // Create the Phone, which fails.
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        restPhoneMockMvc.perform(post("/api/phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isBadRequest());

        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhones() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        // Get all the phoneList
        restPhoneMockMvc.perform(get("/api/phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phone.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getPhone() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        // Get the phone
        restPhoneMockMvc.perform(get("/api/phones/{id}", phone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phone.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhone() throws Exception {
        // Get the phone
        restPhoneMockMvc.perform(get("/api/phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhone() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);
        int databaseSizeBeforeUpdate = phoneRepository.findAll().size();

        // Update the phone
        Phone updatedPhone = phoneRepository.findOne(phone.getId());
        updatedPhone
            .number(UPDATED_NUMBER);
        PhoneDTO phoneDTO = phoneMapper.toDto(updatedPhone);

        restPhoneMockMvc.perform(put("/api/phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isOk());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeUpdate);
        Phone testPhone = phoneList.get(phoneList.size() - 1);
        assertThat(testPhone.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingPhone() throws Exception {
        int databaseSizeBeforeUpdate = phoneRepository.findAll().size();

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPhoneMockMvc.perform(put("/api/phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePhone() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);
        int databaseSizeBeforeDelete = phoneRepository.findAll().size();

        // Get the phone
        restPhoneMockMvc.perform(delete("/api/phones/{id}", phone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phone.class);
        Phone phone1 = new Phone();
        phone1.setId(1L);
        Phone phone2 = new Phone();
        phone2.setId(phone1.getId());
        assertThat(phone1).isEqualTo(phone2);
        phone2.setId(2L);
        assertThat(phone1).isNotEqualTo(phone2);
        phone1.setId(null);
        assertThat(phone1).isNotEqualTo(phone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneDTO.class);
        PhoneDTO phoneDTO1 = new PhoneDTO();
        phoneDTO1.setId(1L);
        PhoneDTO phoneDTO2 = new PhoneDTO();
        assertThat(phoneDTO1).isNotEqualTo(phoneDTO2);
        phoneDTO2.setId(phoneDTO1.getId());
        assertThat(phoneDTO1).isEqualTo(phoneDTO2);
        phoneDTO2.setId(2L);
        assertThat(phoneDTO1).isNotEqualTo(phoneDTO2);
        phoneDTO1.setId(null);
        assertThat(phoneDTO1).isNotEqualTo(phoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(phoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(phoneMapper.fromId(null)).isNull();
    }
}
