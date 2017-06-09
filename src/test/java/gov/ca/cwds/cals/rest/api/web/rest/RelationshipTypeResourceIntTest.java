package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.RelationshipType;
import gov.ca.cwds.cals.rest.api.repository.RelationshipTypeRepository;
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
 * Test class for the RelationshipTypeResource REST controller.
 *
 * @see RelationshipTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class RelationshipTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRelationshipTypeMockMvc;

    private RelationshipType relationshipType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RelationshipTypeResource relationshipTypeResource = new RelationshipTypeResource(relationshipTypeRepository);
        this.restRelationshipTypeMockMvc = MockMvcBuilders.standaloneSetup(relationshipTypeResource)
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
    public static RelationshipType createEntity(EntityManager em) {
        RelationshipType relationshipType = new RelationshipType()
            .name(DEFAULT_NAME);
        return relationshipType;
    }

    @Before
    public void initTest() {
        relationshipType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationshipType() throws Exception {
        int databaseSizeBeforeCreate = relationshipTypeRepository.findAll().size();

        // Create the RelationshipType
        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipType)))
            .andExpect(status().isCreated());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRelationshipTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipTypeRepository.findAll().size();

        // Create the RelationshipType with an existing ID
        relationshipType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipTypeRepository.findAll().size();
        // set the field null
        relationshipType.setName(null);

        // Create the RelationshipType, which fails.

        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipType)))
            .andExpect(status().isBadRequest());

        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypes() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get the relationshipType
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types/{id}", relationshipType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relationshipType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRelationshipType() throws Exception {
        // Get the relationshipType
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Update the relationshipType
        RelationshipType updatedRelationshipType = relationshipTypeRepository.findOne(relationshipType.getId());
        updatedRelationshipType
            .name(UPDATED_NAME);

        restRelationshipTypeMockMvc.perform(put("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelationshipType)))
            .andExpect(status().isOk());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Create the RelationshipType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRelationshipTypeMockMvc.perform(put("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipType)))
            .andExpect(status().isCreated());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);
        int databaseSizeBeforeDelete = relationshipTypeRepository.findAll().size();

        // Get the relationshipType
        restRelationshipTypeMockMvc.perform(delete("/api/relationship-types/{id}", relationshipType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipType.class);
        RelationshipType relationshipType1 = new RelationshipType();
        relationshipType1.setId(1L);
        RelationshipType relationshipType2 = new RelationshipType();
        relationshipType2.setId(relationshipType1.getId());
        assertThat(relationshipType1).isEqualTo(relationshipType2);
        relationshipType2.setId(2L);
        assertThat(relationshipType1).isNotEqualTo(relationshipType2);
        relationshipType1.setId(null);
        assertThat(relationshipType1).isNotEqualTo(relationshipType2);
    }
}
