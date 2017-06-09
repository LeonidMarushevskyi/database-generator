package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.CountyType;
import gov.ca.cwds.cals.rest.api.repository.CountyTypeRepository;
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
 * Test class for the CountyTypeResource REST controller.
 *
 * @see CountyTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class CountyTypeResourceIntTest {

    private static final String DEFAULT_FIPS_CODE = "AAA";
    private static final String UPDATED_FIPS_CODE = "BBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CountyTypeRepository countyTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCountyTypeMockMvc;

    private CountyType countyType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CountyTypeResource countyTypeResource = new CountyTypeResource(countyTypeRepository);
        this.restCountyTypeMockMvc = MockMvcBuilders.standaloneSetup(countyTypeResource)
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
    public static CountyType createEntity(EntityManager em) {
        CountyType countyType = new CountyType()
            .fipsCode(DEFAULT_FIPS_CODE)
            .description(DEFAULT_DESCRIPTION);
        return countyType;
    }

    @Before
    public void initTest() {
        countyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountyType() throws Exception {
        int databaseSizeBeforeCreate = countyTypeRepository.findAll().size();

        // Create the CountyType
        restCountyTypeMockMvc.perform(post("/api/county-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countyType)))
            .andExpect(status().isCreated());

        // Validate the CountyType in the database
        List<CountyType> countyTypeList = countyTypeRepository.findAll();
        assertThat(countyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CountyType testCountyType = countyTypeList.get(countyTypeList.size() - 1);
        assertThat(testCountyType.getFipsCode()).isEqualTo(DEFAULT_FIPS_CODE);
        assertThat(testCountyType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCountyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countyTypeRepository.findAll().size();

        // Create the CountyType with an existing ID
        countyType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountyTypeMockMvc.perform(post("/api/county-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countyType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CountyType> countyTypeList = countyTypeRepository.findAll();
        assertThat(countyTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFipsCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = countyTypeRepository.findAll().size();
        // set the field null
        countyType.setFipsCode(null);

        // Create the CountyType, which fails.

        restCountyTypeMockMvc.perform(post("/api/county-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countyType)))
            .andExpect(status().isBadRequest());

        List<CountyType> countyTypeList = countyTypeRepository.findAll();
        assertThat(countyTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountyTypes() throws Exception {
        // Initialize the database
        countyTypeRepository.saveAndFlush(countyType);

        // Get all the countyTypeList
        restCountyTypeMockMvc.perform(get("/api/county-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].fipsCode").value(hasItem(DEFAULT_FIPS_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCountyType() throws Exception {
        // Initialize the database
        countyTypeRepository.saveAndFlush(countyType);

        // Get the countyType
        restCountyTypeMockMvc.perform(get("/api/county-types/{id}", countyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countyType.getId().intValue()))
            .andExpect(jsonPath("$.fipsCode").value(DEFAULT_FIPS_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCountyType() throws Exception {
        // Get the countyType
        restCountyTypeMockMvc.perform(get("/api/county-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountyType() throws Exception {
        // Initialize the database
        countyTypeRepository.saveAndFlush(countyType);
        int databaseSizeBeforeUpdate = countyTypeRepository.findAll().size();

        // Update the countyType
        CountyType updatedCountyType = countyTypeRepository.findOne(countyType.getId());
        updatedCountyType
            .fipsCode(UPDATED_FIPS_CODE)
            .description(UPDATED_DESCRIPTION);

        restCountyTypeMockMvc.perform(put("/api/county-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountyType)))
            .andExpect(status().isOk());

        // Validate the CountyType in the database
        List<CountyType> countyTypeList = countyTypeRepository.findAll();
        assertThat(countyTypeList).hasSize(databaseSizeBeforeUpdate);
        CountyType testCountyType = countyTypeList.get(countyTypeList.size() - 1);
        assertThat(testCountyType.getFipsCode()).isEqualTo(UPDATED_FIPS_CODE);
        assertThat(testCountyType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCountyType() throws Exception {
        int databaseSizeBeforeUpdate = countyTypeRepository.findAll().size();

        // Create the CountyType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCountyTypeMockMvc.perform(put("/api/county-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countyType)))
            .andExpect(status().isCreated());

        // Validate the CountyType in the database
        List<CountyType> countyTypeList = countyTypeRepository.findAll();
        assertThat(countyTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCountyType() throws Exception {
        // Initialize the database
        countyTypeRepository.saveAndFlush(countyType);
        int databaseSizeBeforeDelete = countyTypeRepository.findAll().size();

        // Get the countyType
        restCountyTypeMockMvc.perform(delete("/api/county-types/{id}", countyType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CountyType> countyTypeList = countyTypeRepository.findAll();
        assertThat(countyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountyType.class);
        CountyType countyType1 = new CountyType();
        countyType1.setId(1L);
        CountyType countyType2 = new CountyType();
        countyType2.setId(countyType1.getId());
        assertThat(countyType1).isEqualTo(countyType2);
        countyType2.setId(2L);
        assertThat(countyType1).isNotEqualTo(countyType2);
        countyType1.setId(null);
        assertThat(countyType1).isNotEqualTo(countyType2);
    }
}
