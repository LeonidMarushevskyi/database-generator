package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.SiblingGroupType;
import gov.ca.cwds.cals.rest.api.repository.SiblingGroupTypeRepository;
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
 * Test class for the SiblingGroupTypeResource REST controller.
 *
 * @see SiblingGroupTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class SiblingGroupTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SiblingGroupTypeRepository siblingGroupTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSiblingGroupTypeMockMvc;

    private SiblingGroupType siblingGroupType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SiblingGroupTypeResource siblingGroupTypeResource = new SiblingGroupTypeResource(siblingGroupTypeRepository);
        this.restSiblingGroupTypeMockMvc = MockMvcBuilders.standaloneSetup(siblingGroupTypeResource)
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
    public static SiblingGroupType createEntity(EntityManager em) {
        SiblingGroupType siblingGroupType = new SiblingGroupType()
            .name(DEFAULT_NAME);
        return siblingGroupType;
    }

    @Before
    public void initTest() {
        siblingGroupType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiblingGroupType() throws Exception {
        int databaseSizeBeforeCreate = siblingGroupTypeRepository.findAll().size();

        // Create the SiblingGroupType
        restSiblingGroupTypeMockMvc.perform(post("/api/sibling-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siblingGroupType)))
            .andExpect(status().isCreated());

        // Validate the SiblingGroupType in the database
        List<SiblingGroupType> siblingGroupTypeList = siblingGroupTypeRepository.findAll();
        assertThat(siblingGroupTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SiblingGroupType testSiblingGroupType = siblingGroupTypeList.get(siblingGroupTypeList.size() - 1);
        assertThat(testSiblingGroupType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSiblingGroupTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siblingGroupTypeRepository.findAll().size();

        // Create the SiblingGroupType with an existing ID
        siblingGroupType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiblingGroupTypeMockMvc.perform(post("/api/sibling-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siblingGroupType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SiblingGroupType> siblingGroupTypeList = siblingGroupTypeRepository.findAll();
        assertThat(siblingGroupTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siblingGroupTypeRepository.findAll().size();
        // set the field null
        siblingGroupType.setName(null);

        // Create the SiblingGroupType, which fails.

        restSiblingGroupTypeMockMvc.perform(post("/api/sibling-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siblingGroupType)))
            .andExpect(status().isBadRequest());

        List<SiblingGroupType> siblingGroupTypeList = siblingGroupTypeRepository.findAll();
        assertThat(siblingGroupTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSiblingGroupTypes() throws Exception {
        // Initialize the database
        siblingGroupTypeRepository.saveAndFlush(siblingGroupType);

        // Get all the siblingGroupTypeList
        restSiblingGroupTypeMockMvc.perform(get("/api/sibling-group-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siblingGroupType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSiblingGroupType() throws Exception {
        // Initialize the database
        siblingGroupTypeRepository.saveAndFlush(siblingGroupType);

        // Get the siblingGroupType
        restSiblingGroupTypeMockMvc.perform(get("/api/sibling-group-types/{id}", siblingGroupType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(siblingGroupType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSiblingGroupType() throws Exception {
        // Get the siblingGroupType
        restSiblingGroupTypeMockMvc.perform(get("/api/sibling-group-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiblingGroupType() throws Exception {
        // Initialize the database
        siblingGroupTypeRepository.saveAndFlush(siblingGroupType);
        int databaseSizeBeforeUpdate = siblingGroupTypeRepository.findAll().size();

        // Update the siblingGroupType
        SiblingGroupType updatedSiblingGroupType = siblingGroupTypeRepository.findOne(siblingGroupType.getId());
        updatedSiblingGroupType
            .name(UPDATED_NAME);

        restSiblingGroupTypeMockMvc.perform(put("/api/sibling-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSiblingGroupType)))
            .andExpect(status().isOk());

        // Validate the SiblingGroupType in the database
        List<SiblingGroupType> siblingGroupTypeList = siblingGroupTypeRepository.findAll();
        assertThat(siblingGroupTypeList).hasSize(databaseSizeBeforeUpdate);
        SiblingGroupType testSiblingGroupType = siblingGroupTypeList.get(siblingGroupTypeList.size() - 1);
        assertThat(testSiblingGroupType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSiblingGroupType() throws Exception {
        int databaseSizeBeforeUpdate = siblingGroupTypeRepository.findAll().size();

        // Create the SiblingGroupType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSiblingGroupTypeMockMvc.perform(put("/api/sibling-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siblingGroupType)))
            .andExpect(status().isCreated());

        // Validate the SiblingGroupType in the database
        List<SiblingGroupType> siblingGroupTypeList = siblingGroupTypeRepository.findAll();
        assertThat(siblingGroupTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSiblingGroupType() throws Exception {
        // Initialize the database
        siblingGroupTypeRepository.saveAndFlush(siblingGroupType);
        int databaseSizeBeforeDelete = siblingGroupTypeRepository.findAll().size();

        // Get the siblingGroupType
        restSiblingGroupTypeMockMvc.perform(delete("/api/sibling-group-types/{id}", siblingGroupType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SiblingGroupType> siblingGroupTypeList = siblingGroupTypeRepository.findAll();
        assertThat(siblingGroupTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiblingGroupType.class);
        SiblingGroupType siblingGroupType1 = new SiblingGroupType();
        siblingGroupType1.setId(1L);
        SiblingGroupType siblingGroupType2 = new SiblingGroupType();
        siblingGroupType2.setId(siblingGroupType1.getId());
        assertThat(siblingGroupType1).isEqualTo(siblingGroupType2);
        siblingGroupType2.setId(2L);
        assertThat(siblingGroupType1).isNotEqualTo(siblingGroupType2);
        siblingGroupType1.setId(null);
        assertThat(siblingGroupType1).isNotEqualTo(siblingGroupType2);
    }
}
