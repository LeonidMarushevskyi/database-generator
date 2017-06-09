package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.RelationshipEventType;
import gov.ca.cwds.cals.rest.api.repository.RelationshipEventTypeRepository;
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
 * Test class for the RelationshipEventTypeResource REST controller.
 *
 * @see RelationshipEventTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class RelationshipEventTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RelationshipEventTypeRepository relationshipEventTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRelationshipEventTypeMockMvc;

    private RelationshipEventType relationshipEventType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RelationshipEventTypeResource relationshipEventTypeResource = new RelationshipEventTypeResource(relationshipEventTypeRepository);
        this.restRelationshipEventTypeMockMvc = MockMvcBuilders.standaloneSetup(relationshipEventTypeResource)
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
    public static RelationshipEventType createEntity(EntityManager em) {
        RelationshipEventType relationshipEventType = new RelationshipEventType()
            .name(DEFAULT_NAME);
        return relationshipEventType;
    }

    @Before
    public void initTest() {
        relationshipEventType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationshipEventType() throws Exception {
        int databaseSizeBeforeCreate = relationshipEventTypeRepository.findAll().size();

        // Create the RelationshipEventType
        restRelationshipEventTypeMockMvc.perform(post("/api/relationship-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEventType)))
            .andExpect(status().isCreated());

        // Validate the RelationshipEventType in the database
        List<RelationshipEventType> relationshipEventTypeList = relationshipEventTypeRepository.findAll();
        assertThat(relationshipEventTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RelationshipEventType testRelationshipEventType = relationshipEventTypeList.get(relationshipEventTypeList.size() - 1);
        assertThat(testRelationshipEventType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRelationshipEventTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipEventTypeRepository.findAll().size();

        // Create the RelationshipEventType with an existing ID
        relationshipEventType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipEventTypeMockMvc.perform(post("/api/relationship-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEventType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RelationshipEventType> relationshipEventTypeList = relationshipEventTypeRepository.findAll();
        assertThat(relationshipEventTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipEventTypeRepository.findAll().size();
        // set the field null
        relationshipEventType.setName(null);

        // Create the RelationshipEventType, which fails.

        restRelationshipEventTypeMockMvc.perform(post("/api/relationship-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEventType)))
            .andExpect(status().isBadRequest());

        List<RelationshipEventType> relationshipEventTypeList = relationshipEventTypeRepository.findAll();
        assertThat(relationshipEventTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelationshipEventTypes() throws Exception {
        // Initialize the database
        relationshipEventTypeRepository.saveAndFlush(relationshipEventType);

        // Get all the relationshipEventTypeList
        restRelationshipEventTypeMockMvc.perform(get("/api/relationship-event-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipEventType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRelationshipEventType() throws Exception {
        // Initialize the database
        relationshipEventTypeRepository.saveAndFlush(relationshipEventType);

        // Get the relationshipEventType
        restRelationshipEventTypeMockMvc.perform(get("/api/relationship-event-types/{id}", relationshipEventType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relationshipEventType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRelationshipEventType() throws Exception {
        // Get the relationshipEventType
        restRelationshipEventTypeMockMvc.perform(get("/api/relationship-event-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationshipEventType() throws Exception {
        // Initialize the database
        relationshipEventTypeRepository.saveAndFlush(relationshipEventType);
        int databaseSizeBeforeUpdate = relationshipEventTypeRepository.findAll().size();

        // Update the relationshipEventType
        RelationshipEventType updatedRelationshipEventType = relationshipEventTypeRepository.findOne(relationshipEventType.getId());
        updatedRelationshipEventType
            .name(UPDATED_NAME);

        restRelationshipEventTypeMockMvc.perform(put("/api/relationship-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelationshipEventType)))
            .andExpect(status().isOk());

        // Validate the RelationshipEventType in the database
        List<RelationshipEventType> relationshipEventTypeList = relationshipEventTypeRepository.findAll();
        assertThat(relationshipEventTypeList).hasSize(databaseSizeBeforeUpdate);
        RelationshipEventType testRelationshipEventType = relationshipEventTypeList.get(relationshipEventTypeList.size() - 1);
        assertThat(testRelationshipEventType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationshipEventType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipEventTypeRepository.findAll().size();

        // Create the RelationshipEventType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRelationshipEventTypeMockMvc.perform(put("/api/relationship-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEventType)))
            .andExpect(status().isCreated());

        // Validate the RelationshipEventType in the database
        List<RelationshipEventType> relationshipEventTypeList = relationshipEventTypeRepository.findAll();
        assertThat(relationshipEventTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRelationshipEventType() throws Exception {
        // Initialize the database
        relationshipEventTypeRepository.saveAndFlush(relationshipEventType);
        int databaseSizeBeforeDelete = relationshipEventTypeRepository.findAll().size();

        // Get the relationshipEventType
        restRelationshipEventTypeMockMvc.perform(delete("/api/relationship-event-types/{id}", relationshipEventType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RelationshipEventType> relationshipEventTypeList = relationshipEventTypeRepository.findAll();
        assertThat(relationshipEventTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipEventType.class);
        RelationshipEventType relationshipEventType1 = new RelationshipEventType();
        relationshipEventType1.setId(1L);
        RelationshipEventType relationshipEventType2 = new RelationshipEventType();
        relationshipEventType2.setId(relationshipEventType1.getId());
        assertThat(relationshipEventType1).isEqualTo(relationshipEventType2);
        relationshipEventType2.setId(2L);
        assertThat(relationshipEventType1).isNotEqualTo(relationshipEventType2);
        relationshipEventType1.setId(null);
        assertThat(relationshipEventType1).isNotEqualTo(relationshipEventType2);
    }
}
