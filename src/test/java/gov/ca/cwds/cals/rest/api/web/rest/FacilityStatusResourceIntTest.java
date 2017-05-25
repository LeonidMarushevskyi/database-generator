package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.FacilityStatus;
import gov.ca.cwds.cals.rest.api.repository.FacilityStatusRepository;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityStatusDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityStatusMapper;
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
 * Test class for the FacilityStatusResource REST controller.
 *
 * @see FacilityStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class FacilityStatusResourceIntTest {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FacilityStatusRepository facilityStatusRepository;

    @Autowired
    private FacilityStatusMapper facilityStatusMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacilityStatusMockMvc;

    private FacilityStatus facilityStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityStatusResource facilityStatusResource = new FacilityStatusResource(facilityStatusRepository, facilityStatusMapper);
        this.restFacilityStatusMockMvc = MockMvcBuilders.standaloneSetup(facilityStatusResource)
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
    public static FacilityStatus createEntity(EntityManager em) {
        FacilityStatus facilityStatus = new FacilityStatus()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return facilityStatus;
    }

    @Before
    public void initTest() {
        facilityStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityStatus() throws Exception {
        int databaseSizeBeforeCreate = facilityStatusRepository.findAll().size();

        // Create the FacilityStatus
        FacilityStatusDTO facilityStatusDTO = facilityStatusMapper.toDto(facilityStatus);
        restFacilityStatusMockMvc.perform(post("/api/facility-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityStatus in the database
        List<FacilityStatus> facilityStatusList = facilityStatusRepository.findAll();
        assertThat(facilityStatusList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityStatus testFacilityStatus = facilityStatusList.get(facilityStatusList.size() - 1);
        assertThat(testFacilityStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFacilityStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFacilityStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityStatusRepository.findAll().size();

        // Create the FacilityStatus with an existing ID
        facilityStatus.setId(1L);
        FacilityStatusDTO facilityStatusDTO = facilityStatusMapper.toDto(facilityStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityStatusMockMvc.perform(post("/api/facility-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FacilityStatus> facilityStatusList = facilityStatusRepository.findAll();
        assertThat(facilityStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityStatusRepository.findAll().size();
        // set the field null
        facilityStatus.setCode(null);

        // Create the FacilityStatus, which fails.
        FacilityStatusDTO facilityStatusDTO = facilityStatusMapper.toDto(facilityStatus);

        restFacilityStatusMockMvc.perform(post("/api/facility-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityStatusDTO)))
            .andExpect(status().isBadRequest());

        List<FacilityStatus> facilityStatusList = facilityStatusRepository.findAll();
        assertThat(facilityStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityStatuses() throws Exception {
        // Initialize the database
        facilityStatusRepository.saveAndFlush(facilityStatus);

        // Get all the facilityStatusList
        restFacilityStatusMockMvc.perform(get("/api/facility-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFacilityStatus() throws Exception {
        // Initialize the database
        facilityStatusRepository.saveAndFlush(facilityStatus);

        // Get the facilityStatus
        restFacilityStatusMockMvc.perform(get("/api/facility-statuses/{id}", facilityStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facilityStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityStatus() throws Exception {
        // Get the facilityStatus
        restFacilityStatusMockMvc.perform(get("/api/facility-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityStatus() throws Exception {
        // Initialize the database
        facilityStatusRepository.saveAndFlush(facilityStatus);
        int databaseSizeBeforeUpdate = facilityStatusRepository.findAll().size();

        // Update the facilityStatus
        FacilityStatus updatedFacilityStatus = facilityStatusRepository.findOne(facilityStatus.getId());
        updatedFacilityStatus
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        FacilityStatusDTO facilityStatusDTO = facilityStatusMapper.toDto(updatedFacilityStatus);

        restFacilityStatusMockMvc.perform(put("/api/facility-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityStatusDTO)))
            .andExpect(status().isOk());

        // Validate the FacilityStatus in the database
        List<FacilityStatus> facilityStatusList = facilityStatusRepository.findAll();
        assertThat(facilityStatusList).hasSize(databaseSizeBeforeUpdate);
        FacilityStatus testFacilityStatus = facilityStatusList.get(facilityStatusList.size() - 1);
        assertThat(testFacilityStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFacilityStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityStatus() throws Exception {
        int databaseSizeBeforeUpdate = facilityStatusRepository.findAll().size();

        // Create the FacilityStatus
        FacilityStatusDTO facilityStatusDTO = facilityStatusMapper.toDto(facilityStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacilityStatusMockMvc.perform(put("/api/facility-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityStatus in the database
        List<FacilityStatus> facilityStatusList = facilityStatusRepository.findAll();
        assertThat(facilityStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacilityStatus() throws Exception {
        // Initialize the database
        facilityStatusRepository.saveAndFlush(facilityStatus);
        int databaseSizeBeforeDelete = facilityStatusRepository.findAll().size();

        // Get the facilityStatus
        restFacilityStatusMockMvc.perform(delete("/api/facility-statuses/{id}", facilityStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityStatus> facilityStatusList = facilityStatusRepository.findAll();
        assertThat(facilityStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityStatus.class);
        FacilityStatus facilityStatus1 = new FacilityStatus();
        facilityStatus1.setId(1L);
        FacilityStatus facilityStatus2 = new FacilityStatus();
        facilityStatus2.setId(facilityStatus1.getId());
        assertThat(facilityStatus1).isEqualTo(facilityStatus2);
        facilityStatus2.setId(2L);
        assertThat(facilityStatus1).isNotEqualTo(facilityStatus2);
        facilityStatus1.setId(null);
        assertThat(facilityStatus1).isNotEqualTo(facilityStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityStatusDTO.class);
        FacilityStatusDTO facilityStatusDTO1 = new FacilityStatusDTO();
        facilityStatusDTO1.setId(1L);
        FacilityStatusDTO facilityStatusDTO2 = new FacilityStatusDTO();
        assertThat(facilityStatusDTO1).isNotEqualTo(facilityStatusDTO2);
        facilityStatusDTO2.setId(facilityStatusDTO1.getId());
        assertThat(facilityStatusDTO1).isEqualTo(facilityStatusDTO2);
        facilityStatusDTO2.setId(2L);
        assertThat(facilityStatusDTO1).isNotEqualTo(facilityStatusDTO2);
        facilityStatusDTO1.setId(null);
        assertThat(facilityStatusDTO1).isNotEqualTo(facilityStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facilityStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facilityStatusMapper.fromId(null)).isNull();
    }
}
