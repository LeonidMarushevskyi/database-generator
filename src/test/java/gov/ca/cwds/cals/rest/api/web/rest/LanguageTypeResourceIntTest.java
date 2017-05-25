package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.LanguageType;
import gov.ca.cwds.cals.rest.api.repository.LanguageTypeRepository;
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
 * Test class for the LanguageTypeResource REST controller.
 *
 * @see LanguageTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class LanguageTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private LanguageTypeRepository languageTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLanguageTypeMockMvc;

    private LanguageType languageType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LanguageTypeResource languageTypeResource = new LanguageTypeResource(languageTypeRepository);
        this.restLanguageTypeMockMvc = MockMvcBuilders.standaloneSetup(languageTypeResource)
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
    public static LanguageType createEntity(EntityManager em) {
        LanguageType languageType = new LanguageType()
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE);
        return languageType;
    }

    @Before
    public void initTest() {
        languageType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLanguageType() throws Exception {
        int databaseSizeBeforeCreate = languageTypeRepository.findAll().size();

        // Create the LanguageType
        restLanguageTypeMockMvc.perform(post("/api/language-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageType)))
            .andExpect(status().isCreated());

        // Validate the LanguageType in the database
        List<LanguageType> languageTypeList = languageTypeRepository.findAll();
        assertThat(languageTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LanguageType testLanguageType = languageTypeList.get(languageTypeList.size() - 1);
        assertThat(testLanguageType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLanguageType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createLanguageTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = languageTypeRepository.findAll().size();

        // Create the LanguageType with an existing ID
        languageType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanguageTypeMockMvc.perform(post("/api/language-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LanguageType> languageTypeList = languageTypeRepository.findAll();
        assertThat(languageTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = languageTypeRepository.findAll().size();
        // set the field null
        languageType.setCode(null);

        // Create the LanguageType, which fails.

        restLanguageTypeMockMvc.perform(post("/api/language-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageType)))
            .andExpect(status().isBadRequest());

        List<LanguageType> languageTypeList = languageTypeRepository.findAll();
        assertThat(languageTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLanguageTypes() throws Exception {
        // Initialize the database
        languageTypeRepository.saveAndFlush(languageType);

        // Get all the languageTypeList
        restLanguageTypeMockMvc.perform(get("/api/language-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(languageType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getLanguageType() throws Exception {
        // Initialize the database
        languageTypeRepository.saveAndFlush(languageType);

        // Get the languageType
        restLanguageTypeMockMvc.perform(get("/api/language-types/{id}", languageType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(languageType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLanguageType() throws Exception {
        // Get the languageType
        restLanguageTypeMockMvc.perform(get("/api/language-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanguageType() throws Exception {
        // Initialize the database
        languageTypeRepository.saveAndFlush(languageType);
        int databaseSizeBeforeUpdate = languageTypeRepository.findAll().size();

        // Update the languageType
        LanguageType updatedLanguageType = languageTypeRepository.findOne(languageType.getId());
        updatedLanguageType
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE);

        restLanguageTypeMockMvc.perform(put("/api/language-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLanguageType)))
            .andExpect(status().isOk());

        // Validate the LanguageType in the database
        List<LanguageType> languageTypeList = languageTypeRepository.findAll();
        assertThat(languageTypeList).hasSize(databaseSizeBeforeUpdate);
        LanguageType testLanguageType = languageTypeList.get(languageTypeList.size() - 1);
        assertThat(testLanguageType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLanguageType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLanguageType() throws Exception {
        int databaseSizeBeforeUpdate = languageTypeRepository.findAll().size();

        // Create the LanguageType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLanguageTypeMockMvc.perform(put("/api/language-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageType)))
            .andExpect(status().isCreated());

        // Validate the LanguageType in the database
        List<LanguageType> languageTypeList = languageTypeRepository.findAll();
        assertThat(languageTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLanguageType() throws Exception {
        // Initialize the database
        languageTypeRepository.saveAndFlush(languageType);
        int databaseSizeBeforeDelete = languageTypeRepository.findAll().size();

        // Get the languageType
        restLanguageTypeMockMvc.perform(delete("/api/language-types/{id}", languageType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LanguageType> languageTypeList = languageTypeRepository.findAll();
        assertThat(languageTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LanguageType.class);
        LanguageType languageType1 = new LanguageType();
        languageType1.setId(1L);
        LanguageType languageType2 = new LanguageType();
        languageType2.setId(languageType1.getId());
        assertThat(languageType1).isEqualTo(languageType2);
        languageType2.setId(2L);
        assertThat(languageType1).isNotEqualTo(languageType2);
        languageType1.setId(null);
        assertThat(languageType1).isNotEqualTo(languageType2);
    }
}
