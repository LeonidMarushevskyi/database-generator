package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.FacilityChild;
import gov.ca.cwds.cals.rest.api.repository.FacilityChildRepository;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityChildDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityChildMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FacilityChildResource REST controller.
 *
 * @see FacilityChildResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class FacilityChildResourceIntTest {

    private static final LocalDate DEFAULT_DATE_OF_PLACEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_PLACEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ASSIGNED_WORKER = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_WORKER = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY_OF_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY_OF_ORIGIN = "BBBBBBBBBB";

    @Autowired
    private FacilityChildRepository facilityChildRepository;

    @Autowired
    private FacilityChildMapper facilityChildMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacilityChildMockMvc;

    private FacilityChild facilityChild;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityChildResource facilityChildResource = new FacilityChildResource(facilityChildRepository, facilityChildMapper);
        this.restFacilityChildMockMvc = MockMvcBuilders.standaloneSetup(facilityChildResource)
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
    public static FacilityChild createEntity(EntityManager em) {
        FacilityChild facilityChild = new FacilityChild()
            .dateOfPlacement(DEFAULT_DATE_OF_PLACEMENT)
            .assignedWorker(DEFAULT_ASSIGNED_WORKER)
            .countyOfOrigin(DEFAULT_COUNTY_OF_ORIGIN);
        return facilityChild;
    }

    @Before
    public void initTest() {
        facilityChild = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityChild() throws Exception {
        int databaseSizeBeforeCreate = facilityChildRepository.findAll().size();

        // Create the FacilityChild
        FacilityChildDTO facilityChildDTO = facilityChildMapper.toDto(facilityChild);
        restFacilityChildMockMvc.perform(post("/api/facility-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityChildDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityChild in the database
        List<FacilityChild> facilityChildList = facilityChildRepository.findAll();
        assertThat(facilityChildList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityChild testFacilityChild = facilityChildList.get(facilityChildList.size() - 1);
        assertThat(testFacilityChild.getDateOfPlacement()).isEqualTo(DEFAULT_DATE_OF_PLACEMENT);
        assertThat(testFacilityChild.getAssignedWorker()).isEqualTo(DEFAULT_ASSIGNED_WORKER);
        assertThat(testFacilityChild.getCountyOfOrigin()).isEqualTo(DEFAULT_COUNTY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void createFacilityChildWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityChildRepository.findAll().size();

        // Create the FacilityChild with an existing ID
        facilityChild.setId(1L);
        FacilityChildDTO facilityChildDTO = facilityChildMapper.toDto(facilityChild);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityChildMockMvc.perform(post("/api/facility-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityChildDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FacilityChild> facilityChildList = facilityChildRepository.findAll();
        assertThat(facilityChildList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCountyOfOriginIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityChildRepository.findAll().size();
        // set the field null
        facilityChild.setCountyOfOrigin(null);

        // Create the FacilityChild, which fails.
        FacilityChildDTO facilityChildDTO = facilityChildMapper.toDto(facilityChild);

        restFacilityChildMockMvc.perform(post("/api/facility-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityChildDTO)))
            .andExpect(status().isBadRequest());

        List<FacilityChild> facilityChildList = facilityChildRepository.findAll();
        assertThat(facilityChildList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityChildren() throws Exception {
        // Initialize the database
        facilityChildRepository.saveAndFlush(facilityChild);

        // Get all the facilityChildList
        restFacilityChildMockMvc.perform(get("/api/facility-children?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityChild.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfPlacement").value(hasItem(DEFAULT_DATE_OF_PLACEMENT.toString())))
            .andExpect(jsonPath("$.[*].assignedWorker").value(hasItem(DEFAULT_ASSIGNED_WORKER.toString())))
            .andExpect(jsonPath("$.[*].countyOfOrigin").value(hasItem(DEFAULT_COUNTY_OF_ORIGIN.toString())));
    }

    @Test
    @Transactional
    public void getFacilityChild() throws Exception {
        // Initialize the database
        facilityChildRepository.saveAndFlush(facilityChild);

        // Get the facilityChild
        restFacilityChildMockMvc.perform(get("/api/facility-children/{id}", facilityChild.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facilityChild.getId().intValue()))
            .andExpect(jsonPath("$.dateOfPlacement").value(DEFAULT_DATE_OF_PLACEMENT.toString()))
            .andExpect(jsonPath("$.assignedWorker").value(DEFAULT_ASSIGNED_WORKER.toString()))
            .andExpect(jsonPath("$.countyOfOrigin").value(DEFAULT_COUNTY_OF_ORIGIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityChild() throws Exception {
        // Get the facilityChild
        restFacilityChildMockMvc.perform(get("/api/facility-children/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityChild() throws Exception {
        // Initialize the database
        facilityChildRepository.saveAndFlush(facilityChild);
        int databaseSizeBeforeUpdate = facilityChildRepository.findAll().size();

        // Update the facilityChild
        FacilityChild updatedFacilityChild = facilityChildRepository.findOne(facilityChild.getId());
        updatedFacilityChild
            .dateOfPlacement(UPDATED_DATE_OF_PLACEMENT)
            .assignedWorker(UPDATED_ASSIGNED_WORKER)
            .countyOfOrigin(UPDATED_COUNTY_OF_ORIGIN);
        FacilityChildDTO facilityChildDTO = facilityChildMapper.toDto(updatedFacilityChild);

        restFacilityChildMockMvc.perform(put("/api/facility-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityChildDTO)))
            .andExpect(status().isOk());

        // Validate the FacilityChild in the database
        List<FacilityChild> facilityChildList = facilityChildRepository.findAll();
        assertThat(facilityChildList).hasSize(databaseSizeBeforeUpdate);
        FacilityChild testFacilityChild = facilityChildList.get(facilityChildList.size() - 1);
        assertThat(testFacilityChild.getDateOfPlacement()).isEqualTo(UPDATED_DATE_OF_PLACEMENT);
        assertThat(testFacilityChild.getAssignedWorker()).isEqualTo(UPDATED_ASSIGNED_WORKER);
        assertThat(testFacilityChild.getCountyOfOrigin()).isEqualTo(UPDATED_COUNTY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityChild() throws Exception {
        int databaseSizeBeforeUpdate = facilityChildRepository.findAll().size();

        // Create the FacilityChild
        FacilityChildDTO facilityChildDTO = facilityChildMapper.toDto(facilityChild);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacilityChildMockMvc.perform(put("/api/facility-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityChildDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityChild in the database
        List<FacilityChild> facilityChildList = facilityChildRepository.findAll();
        assertThat(facilityChildList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacilityChild() throws Exception {
        // Initialize the database
        facilityChildRepository.saveAndFlush(facilityChild);
        int databaseSizeBeforeDelete = facilityChildRepository.findAll().size();

        // Get the facilityChild
        restFacilityChildMockMvc.perform(delete("/api/facility-children/{id}", facilityChild.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityChild> facilityChildList = facilityChildRepository.findAll();
        assertThat(facilityChildList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityChild.class);
        FacilityChild facilityChild1 = new FacilityChild();
        facilityChild1.setId(1L);
        FacilityChild facilityChild2 = new FacilityChild();
        facilityChild2.setId(facilityChild1.getId());
        assertThat(facilityChild1).isEqualTo(facilityChild2);
        facilityChild2.setId(2L);
        assertThat(facilityChild1).isNotEqualTo(facilityChild2);
        facilityChild1.setId(null);
        assertThat(facilityChild1).isNotEqualTo(facilityChild2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityChildDTO.class);
        FacilityChildDTO facilityChildDTO1 = new FacilityChildDTO();
        facilityChildDTO1.setId(1L);
        FacilityChildDTO facilityChildDTO2 = new FacilityChildDTO();
        assertThat(facilityChildDTO1).isNotEqualTo(facilityChildDTO2);
        facilityChildDTO2.setId(facilityChildDTO1.getId());
        assertThat(facilityChildDTO1).isEqualTo(facilityChildDTO2);
        facilityChildDTO2.setId(2L);
        assertThat(facilityChildDTO1).isNotEqualTo(facilityChildDTO2);
        facilityChildDTO1.setId(null);
        assertThat(facilityChildDTO1).isNotEqualTo(facilityChildDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facilityChildMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facilityChildMapper.fromId(null)).isNull();
    }
}
