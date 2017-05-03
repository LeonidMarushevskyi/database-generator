package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.Facility;
import gov.ca.cwds.cals.rest.api.repository.FacilityRepository;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityMapper;
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
 * Test class for the FacilityResource REST controller.
 *
 * @see FacilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class FacilityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LICENSEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSEE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LICENSEE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_LICENSE_NUMBER = 1L;
    private static final Long UPDATED_LICENSE_NUMBER = 2L;

    private static final String DEFAULT_LICENSE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final LocalDate DEFAULT_LICENSE_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LICENSE_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ORIGINAL_APPLICATION_RECIEVED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORIGINAL_APPLICATION_RECIEVED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_VISIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_VISIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_VISIT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_LAST_VISIT_REASON = "BBBBBBBBBB";

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FacilityMapper facilityMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacilityMockMvc;

    private Facility facility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityResource facilityResource = new FacilityResource(facilityRepository, facilityMapper);
        this.restFacilityMockMvc = MockMvcBuilders.standaloneSetup(facilityResource)
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
    public static Facility createEntity(EntityManager em) {
        Facility facility = new Facility()
            .name(DEFAULT_NAME)
            .licenseeName(DEFAULT_LICENSEE_NAME)
            .licenseeType(DEFAULT_LICENSEE_TYPE)
            .licenseNumber(DEFAULT_LICENSE_NUMBER)
            .licenseStatus(DEFAULT_LICENSE_STATUS)
            .capacity(DEFAULT_CAPACITY)
            .licenseEffectiveDate(DEFAULT_LICENSE_EFFECTIVE_DATE)
            .originalApplicationRecievedDate(DEFAULT_ORIGINAL_APPLICATION_RECIEVED_DATE)
            .lastVisitDate(DEFAULT_LAST_VISIT_DATE)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .lastVisitReason(DEFAULT_LAST_VISIT_REASON);
        return facility;
    }

    @Before
    public void initTest() {
        facility = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacility() throws Exception {
        int databaseSizeBeforeCreate = facilityRepository.findAll().size();

        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);
        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeCreate + 1);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacility.getLicenseeName()).isEqualTo(DEFAULT_LICENSEE_NAME);
        assertThat(testFacility.getLicenseeType()).isEqualTo(DEFAULT_LICENSEE_TYPE);
        assertThat(testFacility.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testFacility.getLicenseStatus()).isEqualTo(DEFAULT_LICENSE_STATUS);
        assertThat(testFacility.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testFacility.getLicenseEffectiveDate()).isEqualTo(DEFAULT_LICENSE_EFFECTIVE_DATE);
        assertThat(testFacility.getOriginalApplicationRecievedDate()).isEqualTo(DEFAULT_ORIGINAL_APPLICATION_RECIEVED_DATE);
        assertThat(testFacility.getLastVisitDate()).isEqualTo(DEFAULT_LAST_VISIT_DATE);
        assertThat(testFacility.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testFacility.getLastVisitReason()).isEqualTo(DEFAULT_LAST_VISIT_REASON);
    }

    @Test
    @Transactional
    public void createFacilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityRepository.findAll().size();

        // Create the Facility with an existing ID
        facility.setId(1L);
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityRepository.findAll().size();
        // set the field null
        facility.setName(null);

        // Create the Facility, which fails.
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);

        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isBadRequest());

        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicenseeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityRepository.findAll().size();
        // set the field null
        facility.setLicenseeName(null);

        // Create the Facility, which fails.
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);

        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isBadRequest());

        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicenseNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityRepository.findAll().size();
        // set the field null
        facility.setLicenseNumber(null);

        // Create the Facility, which fails.
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);

        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isBadRequest());

        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityRepository.findAll().size();
        // set the field null
        facility.setCapacity(null);

        // Create the Facility, which fails.
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);

        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isBadRequest());

        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicenseEffectiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityRepository.findAll().size();
        // set the field null
        facility.setLicenseEffectiveDate(null);

        // Create the Facility, which fails.
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);

        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isBadRequest());

        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOriginalApplicationRecievedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityRepository.findAll().size();
        // set the field null
        facility.setOriginalApplicationRecievedDate(null);

        // Create the Facility, which fails.
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);

        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isBadRequest());

        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilities() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList
        restFacilityMockMvc.perform(get("/api/facilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facility.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].licenseeName").value(hasItem(DEFAULT_LICENSEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].licenseeType").value(hasItem(DEFAULT_LICENSEE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].licenseNumber").value(hasItem(DEFAULT_LICENSE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].licenseStatus").value(hasItem(DEFAULT_LICENSE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].licenseEffectiveDate").value(hasItem(DEFAULT_LICENSE_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].originalApplicationRecievedDate").value(hasItem(DEFAULT_ORIGINAL_APPLICATION_RECIEVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastVisitDate").value(hasItem(DEFAULT_LAST_VISIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].lastVisitReason").value(hasItem(DEFAULT_LAST_VISIT_REASON.toString())));
    }

    @Test
    @Transactional
    public void getFacility() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get the facility
        restFacilityMockMvc.perform(get("/api/facilities/{id}", facility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facility.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.licenseeName").value(DEFAULT_LICENSEE_NAME.toString()))
            .andExpect(jsonPath("$.licenseeType").value(DEFAULT_LICENSEE_TYPE.toString()))
            .andExpect(jsonPath("$.licenseNumber").value(DEFAULT_LICENSE_NUMBER.intValue()))
            .andExpect(jsonPath("$.licenseStatus").value(DEFAULT_LICENSE_STATUS.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.licenseEffectiveDate").value(DEFAULT_LICENSE_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.originalApplicationRecievedDate").value(DEFAULT_ORIGINAL_APPLICATION_RECIEVED_DATE.toString()))
            .andExpect(jsonPath("$.lastVisitDate").value(DEFAULT_LAST_VISIT_DATE.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.lastVisitReason").value(DEFAULT_LAST_VISIT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacility() throws Exception {
        // Get the facility
        restFacilityMockMvc.perform(get("/api/facilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacility() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // Update the facility
        Facility updatedFacility = facilityRepository.findOne(facility.getId());
        updatedFacility
            .name(UPDATED_NAME)
            .licenseeName(UPDATED_LICENSEE_NAME)
            .licenseeType(UPDATED_LICENSEE_TYPE)
            .licenseNumber(UPDATED_LICENSE_NUMBER)
            .licenseStatus(UPDATED_LICENSE_STATUS)
            .capacity(UPDATED_CAPACITY)
            .licenseEffectiveDate(UPDATED_LICENSE_EFFECTIVE_DATE)
            .originalApplicationRecievedDate(UPDATED_ORIGINAL_APPLICATION_RECIEVED_DATE)
            .lastVisitDate(UPDATED_LAST_VISIT_DATE)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .lastVisitReason(UPDATED_LAST_VISIT_REASON);
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(updatedFacility);

        restFacilityMockMvc.perform(put("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isOk());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacility.getLicenseeName()).isEqualTo(UPDATED_LICENSEE_NAME);
        assertThat(testFacility.getLicenseeType()).isEqualTo(UPDATED_LICENSEE_TYPE);
        assertThat(testFacility.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testFacility.getLicenseStatus()).isEqualTo(UPDATED_LICENSE_STATUS);
        assertThat(testFacility.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testFacility.getLicenseEffectiveDate()).isEqualTo(UPDATED_LICENSE_EFFECTIVE_DATE);
        assertThat(testFacility.getOriginalApplicationRecievedDate()).isEqualTo(UPDATED_ORIGINAL_APPLICATION_RECIEVED_DATE);
        assertThat(testFacility.getLastVisitDate()).isEqualTo(UPDATED_LAST_VISIT_DATE);
        assertThat(testFacility.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testFacility.getLastVisitReason()).isEqualTo(UPDATED_LAST_VISIT_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacilityMockMvc.perform(put("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacility() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        int databaseSizeBeforeDelete = facilityRepository.findAll().size();

        // Get the facility
        restFacilityMockMvc.perform(delete("/api/facilities/{id}", facility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facility.class);
    }
}
