package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PosessionType;
import gov.ca.cwds.cals.rest.api.repository.PosessionTypeRepository;
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
 * Test class for the PosessionTypeResource REST controller.
 *
 * @see PosessionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PosessionTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PosessionTypeRepository posessionTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPosessionTypeMockMvc;

    private PosessionType posessionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PosessionTypeResource posessionTypeResource = new PosessionTypeResource(posessionTypeRepository);
        this.restPosessionTypeMockMvc = MockMvcBuilders.standaloneSetup(posessionTypeResource)
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
    public static PosessionType createEntity(EntityManager em) {
        PosessionType posessionType = new PosessionType()
            .name(DEFAULT_NAME);
        return posessionType;
    }

    @Before
    public void initTest() {
        posessionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPosessionType() throws Exception {
        int databaseSizeBeforeCreate = posessionTypeRepository.findAll().size();

        // Create the PosessionType
        restPosessionTypeMockMvc.perform(post("/api/posession-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posessionType)))
            .andExpect(status().isCreated());

        // Validate the PosessionType in the database
        List<PosessionType> posessionTypeList = posessionTypeRepository.findAll();
        assertThat(posessionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PosessionType testPosessionType = posessionTypeList.get(posessionTypeList.size() - 1);
        assertThat(testPosessionType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPosessionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = posessionTypeRepository.findAll().size();

        // Create the PosessionType with an existing ID
        posessionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPosessionTypeMockMvc.perform(post("/api/posession-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posessionType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PosessionType> posessionTypeList = posessionTypeRepository.findAll();
        assertThat(posessionTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = posessionTypeRepository.findAll().size();
        // set the field null
        posessionType.setName(null);

        // Create the PosessionType, which fails.

        restPosessionTypeMockMvc.perform(post("/api/posession-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posessionType)))
            .andExpect(status().isBadRequest());

        List<PosessionType> posessionTypeList = posessionTypeRepository.findAll();
        assertThat(posessionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPosessionTypes() throws Exception {
        // Initialize the database
        posessionTypeRepository.saveAndFlush(posessionType);

        // Get all the posessionTypeList
        restPosessionTypeMockMvc.perform(get("/api/posession-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(posessionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPosessionType() throws Exception {
        // Initialize the database
        posessionTypeRepository.saveAndFlush(posessionType);

        // Get the posessionType
        restPosessionTypeMockMvc.perform(get("/api/posession-types/{id}", posessionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(posessionType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPosessionType() throws Exception {
        // Get the posessionType
        restPosessionTypeMockMvc.perform(get("/api/posession-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePosessionType() throws Exception {
        // Initialize the database
        posessionTypeRepository.saveAndFlush(posessionType);
        int databaseSizeBeforeUpdate = posessionTypeRepository.findAll().size();

        // Update the posessionType
        PosessionType updatedPosessionType = posessionTypeRepository.findOne(posessionType.getId());
        updatedPosessionType
            .name(UPDATED_NAME);

        restPosessionTypeMockMvc.perform(put("/api/posession-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPosessionType)))
            .andExpect(status().isOk());

        // Validate the PosessionType in the database
        List<PosessionType> posessionTypeList = posessionTypeRepository.findAll();
        assertThat(posessionTypeList).hasSize(databaseSizeBeforeUpdate);
        PosessionType testPosessionType = posessionTypeList.get(posessionTypeList.size() - 1);
        assertThat(testPosessionType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPosessionType() throws Exception {
        int databaseSizeBeforeUpdate = posessionTypeRepository.findAll().size();

        // Create the PosessionType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPosessionTypeMockMvc.perform(put("/api/posession-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posessionType)))
            .andExpect(status().isCreated());

        // Validate the PosessionType in the database
        List<PosessionType> posessionTypeList = posessionTypeRepository.findAll();
        assertThat(posessionTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePosessionType() throws Exception {
        // Initialize the database
        posessionTypeRepository.saveAndFlush(posessionType);
        int databaseSizeBeforeDelete = posessionTypeRepository.findAll().size();

        // Get the posessionType
        restPosessionTypeMockMvc.perform(delete("/api/posession-types/{id}", posessionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PosessionType> posessionTypeList = posessionTypeRepository.findAll();
        assertThat(posessionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PosessionType.class);
        PosessionType posessionType1 = new PosessionType();
        posessionType1.setId(1L);
        PosessionType posessionType2 = new PosessionType();
        posessionType2.setId(posessionType1.getId());
        assertThat(posessionType1).isEqualTo(posessionType2);
        posessionType2.setId(2L);
        assertThat(posessionType1).isNotEqualTo(posessionType2);
        posessionType1.setId(null);
        assertThat(posessionType1).isNotEqualTo(posessionType2);
    }
}
