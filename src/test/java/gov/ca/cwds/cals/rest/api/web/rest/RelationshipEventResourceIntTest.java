package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.RelationshipEvent;
import gov.ca.cwds.cals.rest.api.repository.RelationshipEventRepository;
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
 * Test class for the RelationshipEventResource REST controller.
 *
 * @see RelationshipEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class RelationshipEventResourceIntTest {

    private static final String DEFAULT_EVENT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_PLACE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EVENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RelationshipEventRepository relationshipEventRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRelationshipEventMockMvc;

    private RelationshipEvent relationshipEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RelationshipEventResource relationshipEventResource = new RelationshipEventResource(relationshipEventRepository);
        this.restRelationshipEventMockMvc = MockMvcBuilders.standaloneSetup(relationshipEventResource)
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
    public static RelationshipEvent createEntity(EntityManager em) {
        RelationshipEvent relationshipEvent = new RelationshipEvent()
            .eventPlace(DEFAULT_EVENT_PLACE)
            .eventDate(DEFAULT_EVENT_DATE)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return relationshipEvent;
    }

    @Before
    public void initTest() {
        relationshipEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationshipEvent() throws Exception {
        int databaseSizeBeforeCreate = relationshipEventRepository.findAll().size();

        // Create the RelationshipEvent
        restRelationshipEventMockMvc.perform(post("/api/relationship-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEvent)))
            .andExpect(status().isCreated());

        // Validate the RelationshipEvent in the database
        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeCreate + 1);
        RelationshipEvent testRelationshipEvent = relationshipEventList.get(relationshipEventList.size() - 1);
        assertThat(testRelationshipEvent.getEventPlace()).isEqualTo(DEFAULT_EVENT_PLACE);
        assertThat(testRelationshipEvent.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testRelationshipEvent.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testRelationshipEvent.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testRelationshipEvent.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testRelationshipEvent.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createRelationshipEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipEventRepository.findAll().size();

        // Create the RelationshipEvent with an existing ID
        relationshipEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipEventMockMvc.perform(post("/api/relationship-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEvent)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipEventRepository.findAll().size();
        // set the field null
        relationshipEvent.setCreateUserId(null);

        // Create the RelationshipEvent, which fails.

        restRelationshipEventMockMvc.perform(post("/api/relationship-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEvent)))
            .andExpect(status().isBadRequest());

        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipEventRepository.findAll().size();
        // set the field null
        relationshipEvent.setCreateDateTime(null);

        // Create the RelationshipEvent, which fails.

        restRelationshipEventMockMvc.perform(post("/api/relationship-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEvent)))
            .andExpect(status().isBadRequest());

        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipEventRepository.findAll().size();
        // set the field null
        relationshipEvent.setUpdateUserId(null);

        // Create the RelationshipEvent, which fails.

        restRelationshipEventMockMvc.perform(post("/api/relationship-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEvent)))
            .andExpect(status().isBadRequest());

        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipEventRepository.findAll().size();
        // set the field null
        relationshipEvent.setUpdateDateTime(null);

        // Create the RelationshipEvent, which fails.

        restRelationshipEventMockMvc.perform(post("/api/relationship-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEvent)))
            .andExpect(status().isBadRequest());

        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelationshipEvents() throws Exception {
        // Initialize the database
        relationshipEventRepository.saveAndFlush(relationshipEvent);

        // Get all the relationshipEventList
        restRelationshipEventMockMvc.perform(get("/api/relationship-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventPlace").value(hasItem(DEFAULT_EVENT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getRelationshipEvent() throws Exception {
        // Initialize the database
        relationshipEventRepository.saveAndFlush(relationshipEvent);

        // Get the relationshipEvent
        restRelationshipEventMockMvc.perform(get("/api/relationship-events/{id}", relationshipEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relationshipEvent.getId().intValue()))
            .andExpect(jsonPath("$.eventPlace").value(DEFAULT_EVENT_PLACE.toString()))
            .andExpect(jsonPath("$.eventDate").value(DEFAULT_EVENT_DATE.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingRelationshipEvent() throws Exception {
        // Get the relationshipEvent
        restRelationshipEventMockMvc.perform(get("/api/relationship-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationshipEvent() throws Exception {
        // Initialize the database
        relationshipEventRepository.saveAndFlush(relationshipEvent);
        int databaseSizeBeforeUpdate = relationshipEventRepository.findAll().size();

        // Update the relationshipEvent
        RelationshipEvent updatedRelationshipEvent = relationshipEventRepository.findOne(relationshipEvent.getId());
        updatedRelationshipEvent
            .eventPlace(UPDATED_EVENT_PLACE)
            .eventDate(UPDATED_EVENT_DATE)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restRelationshipEventMockMvc.perform(put("/api/relationship-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelationshipEvent)))
            .andExpect(status().isOk());

        // Validate the RelationshipEvent in the database
        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeUpdate);
        RelationshipEvent testRelationshipEvent = relationshipEventList.get(relationshipEventList.size() - 1);
        assertThat(testRelationshipEvent.getEventPlace()).isEqualTo(UPDATED_EVENT_PLACE);
        assertThat(testRelationshipEvent.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testRelationshipEvent.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testRelationshipEvent.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testRelationshipEvent.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testRelationshipEvent.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationshipEvent() throws Exception {
        int databaseSizeBeforeUpdate = relationshipEventRepository.findAll().size();

        // Create the RelationshipEvent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRelationshipEventMockMvc.perform(put("/api/relationship-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipEvent)))
            .andExpect(status().isCreated());

        // Validate the RelationshipEvent in the database
        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRelationshipEvent() throws Exception {
        // Initialize the database
        relationshipEventRepository.saveAndFlush(relationshipEvent);
        int databaseSizeBeforeDelete = relationshipEventRepository.findAll().size();

        // Get the relationshipEvent
        restRelationshipEventMockMvc.perform(delete("/api/relationship-events/{id}", relationshipEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RelationshipEvent> relationshipEventList = relationshipEventRepository.findAll();
        assertThat(relationshipEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipEvent.class);
        RelationshipEvent relationshipEvent1 = new RelationshipEvent();
        relationshipEvent1.setId(1L);
        RelationshipEvent relationshipEvent2 = new RelationshipEvent();
        relationshipEvent2.setId(relationshipEvent1.getId());
        assertThat(relationshipEvent1).isEqualTo(relationshipEvent2);
        relationshipEvent2.setId(2L);
        assertThat(relationshipEvent1).isNotEqualTo(relationshipEvent2);
        relationshipEvent1.setId(null);
        assertThat(relationshipEvent1).isNotEqualTo(relationshipEvent2);
    }
}
