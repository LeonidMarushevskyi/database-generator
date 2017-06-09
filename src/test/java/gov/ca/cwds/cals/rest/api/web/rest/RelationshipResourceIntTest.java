package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.Relationship;
import gov.ca.cwds.cals.rest.api.domain.Person;
import gov.ca.cwds.cals.rest.api.domain.Person;
import gov.ca.cwds.cals.rest.api.repository.RelationshipRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static gov.ca.cwds.cals.rest.api.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RelationshipResource REST controller.
 *
 * @see RelationshipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class RelationshipResourceIntTest {

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRelationshipMockMvc;

    private Relationship relationship;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RelationshipResource relationshipResource = new RelationshipResource(relationshipRepository);
        this.restRelationshipMockMvc = MockMvcBuilders.standaloneSetup(relationshipResource)
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
    public static Relationship createEntity(EntityManager em) {
        Relationship relationship = new Relationship()
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        // Add required entity
        Person from = PersonResourceIntTest.createEntity(em);
        em.persist(from);
        em.flush();
        relationship.setFrom(from);
        // Add required entity
        Person to = PersonResourceIntTest.createEntity(em);
        em.persist(to);
        em.flush();
        relationship.setTo(to);
        return relationship;
    }

    @Before
    public void initTest() {
        relationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationship() throws Exception {
        int databaseSizeBeforeCreate = relationshipRepository.findAll().size();

        // Create the Relationship
        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationship)))
            .andExpect(status().isCreated());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeCreate + 1);
        Relationship testRelationship = relationshipList.get(relationshipList.size() - 1);
        assertThat(testRelationship.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testRelationship.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testRelationship.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testRelationship.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipRepository.findAll().size();

        // Create the Relationship with an existing ID
        relationship.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationship)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipRepository.findAll().size();
        // set the field null
        relationship.setCreateUserId(null);

        // Create the Relationship, which fails.

        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationship)))
            .andExpect(status().isBadRequest());

        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipRepository.findAll().size();
        // set the field null
        relationship.setCreateDateTime(null);

        // Create the Relationship, which fails.

        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationship)))
            .andExpect(status().isBadRequest());

        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipRepository.findAll().size();
        // set the field null
        relationship.setUpdateUserId(null);

        // Create the Relationship, which fails.

        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationship)))
            .andExpect(status().isBadRequest());

        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipRepository.findAll().size();
        // set the field null
        relationship.setUpdateDateTime(null);

        // Create the Relationship, which fails.

        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationship)))
            .andExpect(status().isBadRequest());

        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelationships() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);

        // Get all the relationshipList
        restRelationshipMockMvc.perform(get("/api/relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);

        // Get the relationship
        restRelationshipMockMvc.perform(get("/api/relationships/{id}", relationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relationship.getId().intValue()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingRelationship() throws Exception {
        // Get the relationship
        restRelationshipMockMvc.perform(get("/api/relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);
        int databaseSizeBeforeUpdate = relationshipRepository.findAll().size();

        // Update the relationship
        Relationship updatedRelationship = relationshipRepository.findOne(relationship.getId());
        updatedRelationship
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restRelationshipMockMvc.perform(put("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelationship)))
            .andExpect(status().isOk());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeUpdate);
        Relationship testRelationship = relationshipList.get(relationshipList.size() - 1);
        assertThat(testRelationship.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testRelationship.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testRelationship.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testRelationship.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationship() throws Exception {
        int databaseSizeBeforeUpdate = relationshipRepository.findAll().size();

        // Create the Relationship

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRelationshipMockMvc.perform(put("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationship)))
            .andExpect(status().isCreated());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);
        int databaseSizeBeforeDelete = relationshipRepository.findAll().size();

        // Get the relationship
        restRelationshipMockMvc.perform(delete("/api/relationships/{id}", relationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Relationship.class);
        Relationship relationship1 = new Relationship();
        relationship1.setId(1L);
        Relationship relationship2 = new Relationship();
        relationship2.setId(relationship1.getId());
        assertThat(relationship1).isEqualTo(relationship2);
        relationship2.setId(2L);
        assertThat(relationship1).isNotEqualTo(relationship2);
        relationship1.setId(null);
        assertThat(relationship1).isNotEqualTo(relationship2);
    }
}
