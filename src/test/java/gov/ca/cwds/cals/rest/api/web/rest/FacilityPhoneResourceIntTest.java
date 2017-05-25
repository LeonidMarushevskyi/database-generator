package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.FacilityPhone;
import gov.ca.cwds.cals.rest.api.repository.FacilityPhoneRepository;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityPhoneDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityPhoneMapper;
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
 * Test class for the FacilityPhoneResource REST controller.
 *
 * @see FacilityPhoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class FacilityPhoneResourceIntTest {

    @Autowired
    private FacilityPhoneRepository facilityPhoneRepository;

    @Autowired
    private FacilityPhoneMapper facilityPhoneMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacilityPhoneMockMvc;

    private FacilityPhone facilityPhone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityPhoneResource facilityPhoneResource = new FacilityPhoneResource(facilityPhoneRepository, facilityPhoneMapper);
        this.restFacilityPhoneMockMvc = MockMvcBuilders.standaloneSetup(facilityPhoneResource)
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
    public static FacilityPhone createEntity(EntityManager em) {
        FacilityPhone facilityPhone = new FacilityPhone();
        return facilityPhone;
    }

    @Before
    public void initTest() {
        facilityPhone = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityPhone() throws Exception {
        int databaseSizeBeforeCreate = facilityPhoneRepository.findAll().size();

        // Create the FacilityPhone
        FacilityPhoneDTO facilityPhoneDTO = facilityPhoneMapper.toDto(facilityPhone);
        restFacilityPhoneMockMvc.perform(post("/api/facility-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityPhoneDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityPhone in the database
        List<FacilityPhone> facilityPhoneList = facilityPhoneRepository.findAll();
        assertThat(facilityPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityPhone testFacilityPhone = facilityPhoneList.get(facilityPhoneList.size() - 1);
    }

    @Test
    @Transactional
    public void createFacilityPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityPhoneRepository.findAll().size();

        // Create the FacilityPhone with an existing ID
        facilityPhone.setId(1L);
        FacilityPhoneDTO facilityPhoneDTO = facilityPhoneMapper.toDto(facilityPhone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityPhoneMockMvc.perform(post("/api/facility-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FacilityPhone> facilityPhoneList = facilityPhoneRepository.findAll();
        assertThat(facilityPhoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFacilityPhones() throws Exception {
        // Initialize the database
        facilityPhoneRepository.saveAndFlush(facilityPhone);

        // Get all the facilityPhoneList
        restFacilityPhoneMockMvc.perform(get("/api/facility-phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityPhone.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFacilityPhone() throws Exception {
        // Initialize the database
        facilityPhoneRepository.saveAndFlush(facilityPhone);

        // Get the facilityPhone
        restFacilityPhoneMockMvc.perform(get("/api/facility-phones/{id}", facilityPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facilityPhone.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityPhone() throws Exception {
        // Get the facilityPhone
        restFacilityPhoneMockMvc.perform(get("/api/facility-phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityPhone() throws Exception {
        // Initialize the database
        facilityPhoneRepository.saveAndFlush(facilityPhone);
        int databaseSizeBeforeUpdate = facilityPhoneRepository.findAll().size();

        // Update the facilityPhone
        FacilityPhone updatedFacilityPhone = facilityPhoneRepository.findOne(facilityPhone.getId());
        FacilityPhoneDTO facilityPhoneDTO = facilityPhoneMapper.toDto(updatedFacilityPhone);

        restFacilityPhoneMockMvc.perform(put("/api/facility-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityPhoneDTO)))
            .andExpect(status().isOk());

        // Validate the FacilityPhone in the database
        List<FacilityPhone> facilityPhoneList = facilityPhoneRepository.findAll();
        assertThat(facilityPhoneList).hasSize(databaseSizeBeforeUpdate);
        FacilityPhone testFacilityPhone = facilityPhoneList.get(facilityPhoneList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityPhone() throws Exception {
        int databaseSizeBeforeUpdate = facilityPhoneRepository.findAll().size();

        // Create the FacilityPhone
        FacilityPhoneDTO facilityPhoneDTO = facilityPhoneMapper.toDto(facilityPhone);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacilityPhoneMockMvc.perform(put("/api/facility-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityPhoneDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityPhone in the database
        List<FacilityPhone> facilityPhoneList = facilityPhoneRepository.findAll();
        assertThat(facilityPhoneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacilityPhone() throws Exception {
        // Initialize the database
        facilityPhoneRepository.saveAndFlush(facilityPhone);
        int databaseSizeBeforeDelete = facilityPhoneRepository.findAll().size();

        // Get the facilityPhone
        restFacilityPhoneMockMvc.perform(delete("/api/facility-phones/{id}", facilityPhone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityPhone> facilityPhoneList = facilityPhoneRepository.findAll();
        assertThat(facilityPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityPhone.class);
        FacilityPhone facilityPhone1 = new FacilityPhone();
        facilityPhone1.setId(1L);
        FacilityPhone facilityPhone2 = new FacilityPhone();
        facilityPhone2.setId(facilityPhone1.getId());
        assertThat(facilityPhone1).isEqualTo(facilityPhone2);
        facilityPhone2.setId(2L);
        assertThat(facilityPhone1).isNotEqualTo(facilityPhone2);
        facilityPhone1.setId(null);
        assertThat(facilityPhone1).isNotEqualTo(facilityPhone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityPhoneDTO.class);
        FacilityPhoneDTO facilityPhoneDTO1 = new FacilityPhoneDTO();
        facilityPhoneDTO1.setId(1L);
        FacilityPhoneDTO facilityPhoneDTO2 = new FacilityPhoneDTO();
        assertThat(facilityPhoneDTO1).isNotEqualTo(facilityPhoneDTO2);
        facilityPhoneDTO2.setId(facilityPhoneDTO1.getId());
        assertThat(facilityPhoneDTO1).isEqualTo(facilityPhoneDTO2);
        facilityPhoneDTO2.setId(2L);
        assertThat(facilityPhoneDTO1).isNotEqualTo(facilityPhoneDTO2);
        facilityPhoneDTO1.setId(null);
        assertThat(facilityPhoneDTO1).isNotEqualTo(facilityPhoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facilityPhoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facilityPhoneMapper.fromId(null)).isNull();
    }
}
