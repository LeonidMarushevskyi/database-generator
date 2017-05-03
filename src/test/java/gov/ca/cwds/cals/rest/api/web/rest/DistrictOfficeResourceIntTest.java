package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.DistrictOffice;
import gov.ca.cwds.cals.rest.api.repository.DistrictOfficeRepository;
import gov.ca.cwds.cals.rest.api.service.dto.DistrictOfficeDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.DistrictOfficeMapper;
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
 * Test class for the DistrictOfficeResource REST controller.
 *
 * @see DistrictOfficeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class DistrictOfficeResourceIntTest {

    private static final Long DEFAULT_FACILITY_NUMBER = 1L;
    private static final Long UPDATED_FACILITY_NUMBER = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DistrictOfficeRepository districtOfficeRepository;

    @Autowired
    private DistrictOfficeMapper districtOfficeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDistrictOfficeMockMvc;

    private DistrictOffice districtOffice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DistrictOfficeResource districtOfficeResource = new DistrictOfficeResource(districtOfficeRepository, districtOfficeMapper);
        this.restDistrictOfficeMockMvc = MockMvcBuilders.standaloneSetup(districtOfficeResource)
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
    public static DistrictOffice createEntity(EntityManager em) {
        DistrictOffice districtOffice = new DistrictOffice()
            .facilityNumber(DEFAULT_FACILITY_NUMBER)
            .name(DEFAULT_NAME);
        return districtOffice;
    }

    @Before
    public void initTest() {
        districtOffice = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistrictOffice() throws Exception {
        int databaseSizeBeforeCreate = districtOfficeRepository.findAll().size();

        // Create the DistrictOffice
        DistrictOfficeDTO districtOfficeDTO = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(districtOffice);
        restDistrictOfficeMockMvc.perform(post("/api/district-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtOfficeDTO)))
            .andExpect(status().isCreated());

        // Validate the DistrictOffice in the database
        List<DistrictOffice> districtOfficeList = districtOfficeRepository.findAll();
        assertThat(districtOfficeList).hasSize(databaseSizeBeforeCreate + 1);
        DistrictOffice testDistrictOffice = districtOfficeList.get(districtOfficeList.size() - 1);
        assertThat(testDistrictOffice.getFacilityNumber()).isEqualTo(DEFAULT_FACILITY_NUMBER);
        assertThat(testDistrictOffice.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDistrictOfficeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = districtOfficeRepository.findAll().size();

        // Create the DistrictOffice with an existing ID
        districtOffice.setId(1L);
        DistrictOfficeDTO districtOfficeDTO = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(districtOffice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictOfficeMockMvc.perform(post("/api/district-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtOfficeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DistrictOffice> districtOfficeList = districtOfficeRepository.findAll();
        assertThat(districtOfficeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFacilityNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtOfficeRepository.findAll().size();
        // set the field null
        districtOffice.setFacilityNumber(null);

        // Create the DistrictOffice, which fails.
        DistrictOfficeDTO districtOfficeDTO = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(districtOffice);

        restDistrictOfficeMockMvc.perform(post("/api/district-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtOfficeDTO)))
            .andExpect(status().isBadRequest());

        List<DistrictOffice> districtOfficeList = districtOfficeRepository.findAll();
        assertThat(districtOfficeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtOfficeRepository.findAll().size();
        // set the field null
        districtOffice.setName(null);

        // Create the DistrictOffice, which fails.
        DistrictOfficeDTO districtOfficeDTO = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(districtOffice);

        restDistrictOfficeMockMvc.perform(post("/api/district-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtOfficeDTO)))
            .andExpect(status().isBadRequest());

        List<DistrictOffice> districtOfficeList = districtOfficeRepository.findAll();
        assertThat(districtOfficeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDistrictOffices() throws Exception {
        // Initialize the database
        districtOfficeRepository.saveAndFlush(districtOffice);

        // Get all the districtOfficeList
        restDistrictOfficeMockMvc.perform(get("/api/district-offices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districtOffice.getId().intValue())))
            .andExpect(jsonPath("$.[*].facilityNumber").value(hasItem(DEFAULT_FACILITY_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDistrictOffice() throws Exception {
        // Initialize the database
        districtOfficeRepository.saveAndFlush(districtOffice);

        // Get the districtOffice
        restDistrictOfficeMockMvc.perform(get("/api/district-offices/{id}", districtOffice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(districtOffice.getId().intValue()))
            .andExpect(jsonPath("$.facilityNumber").value(DEFAULT_FACILITY_NUMBER.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDistrictOffice() throws Exception {
        // Get the districtOffice
        restDistrictOfficeMockMvc.perform(get("/api/district-offices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrictOffice() throws Exception {
        // Initialize the database
        districtOfficeRepository.saveAndFlush(districtOffice);
        int databaseSizeBeforeUpdate = districtOfficeRepository.findAll().size();

        // Update the districtOffice
        DistrictOffice updatedDistrictOffice = districtOfficeRepository.findOne(districtOffice.getId());
        updatedDistrictOffice
            .facilityNumber(UPDATED_FACILITY_NUMBER)
            .name(UPDATED_NAME);
        DistrictOfficeDTO districtOfficeDTO = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(updatedDistrictOffice);

        restDistrictOfficeMockMvc.perform(put("/api/district-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtOfficeDTO)))
            .andExpect(status().isOk());

        // Validate the DistrictOffice in the database
        List<DistrictOffice> districtOfficeList = districtOfficeRepository.findAll();
        assertThat(districtOfficeList).hasSize(databaseSizeBeforeUpdate);
        DistrictOffice testDistrictOffice = districtOfficeList.get(districtOfficeList.size() - 1);
        assertThat(testDistrictOffice.getFacilityNumber()).isEqualTo(UPDATED_FACILITY_NUMBER);
        assertThat(testDistrictOffice.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDistrictOffice() throws Exception {
        int databaseSizeBeforeUpdate = districtOfficeRepository.findAll().size();

        // Create the DistrictOffice
        DistrictOfficeDTO districtOfficeDTO = districtOfficeMapper.districtOfficeToDistrictOfficeDTO(districtOffice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDistrictOfficeMockMvc.perform(put("/api/district-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtOfficeDTO)))
            .andExpect(status().isCreated());

        // Validate the DistrictOffice in the database
        List<DistrictOffice> districtOfficeList = districtOfficeRepository.findAll();
        assertThat(districtOfficeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDistrictOffice() throws Exception {
        // Initialize the database
        districtOfficeRepository.saveAndFlush(districtOffice);
        int databaseSizeBeforeDelete = districtOfficeRepository.findAll().size();

        // Get the districtOffice
        restDistrictOfficeMockMvc.perform(delete("/api/district-offices/{id}", districtOffice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DistrictOffice> districtOfficeList = districtOfficeRepository.findAll();
        assertThat(districtOfficeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictOffice.class);
    }
}
