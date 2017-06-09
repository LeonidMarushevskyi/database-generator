package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.BodyOfWater;
import gov.ca.cwds.cals.rest.api.repository.BodyOfWaterRepository;
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
 * Test class for the BodyOfWaterResource REST controller.
 *
 * @see BodyOfWaterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class BodyOfWaterResourceIntTest {

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BodyOfWaterRepository bodyOfWaterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBodyOfWaterMockMvc;

    private BodyOfWater bodyOfWater;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BodyOfWaterResource bodyOfWaterResource = new BodyOfWaterResource(bodyOfWaterRepository);
        this.restBodyOfWaterMockMvc = MockMvcBuilders.standaloneSetup(bodyOfWaterResource)
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
    public static BodyOfWater createEntity(EntityManager em) {
        BodyOfWater bodyOfWater = new BodyOfWater()
            .location(DEFAULT_LOCATION)
            .size(DEFAULT_SIZE)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return bodyOfWater;
    }

    @Before
    public void initTest() {
        bodyOfWater = createEntity(em);
    }

    @Test
    @Transactional
    public void createBodyOfWater() throws Exception {
        int databaseSizeBeforeCreate = bodyOfWaterRepository.findAll().size();

        // Create the BodyOfWater
        restBodyOfWaterMockMvc.perform(post("/api/body-of-waters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyOfWater)))
            .andExpect(status().isCreated());

        // Validate the BodyOfWater in the database
        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeCreate + 1);
        BodyOfWater testBodyOfWater = bodyOfWaterList.get(bodyOfWaterList.size() - 1);
        assertThat(testBodyOfWater.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testBodyOfWater.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testBodyOfWater.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testBodyOfWater.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testBodyOfWater.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testBodyOfWater.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createBodyOfWaterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bodyOfWaterRepository.findAll().size();

        // Create the BodyOfWater with an existing ID
        bodyOfWater.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyOfWaterMockMvc.perform(post("/api/body-of-waters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyOfWater)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bodyOfWaterRepository.findAll().size();
        // set the field null
        bodyOfWater.setCreateUserId(null);

        // Create the BodyOfWater, which fails.

        restBodyOfWaterMockMvc.perform(post("/api/body-of-waters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyOfWater)))
            .andExpect(status().isBadRequest());

        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bodyOfWaterRepository.findAll().size();
        // set the field null
        bodyOfWater.setCreateDateTime(null);

        // Create the BodyOfWater, which fails.

        restBodyOfWaterMockMvc.perform(post("/api/body-of-waters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyOfWater)))
            .andExpect(status().isBadRequest());

        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bodyOfWaterRepository.findAll().size();
        // set the field null
        bodyOfWater.setUpdateUserId(null);

        // Create the BodyOfWater, which fails.

        restBodyOfWaterMockMvc.perform(post("/api/body-of-waters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyOfWater)))
            .andExpect(status().isBadRequest());

        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bodyOfWaterRepository.findAll().size();
        // set the field null
        bodyOfWater.setUpdateDateTime(null);

        // Create the BodyOfWater, which fails.

        restBodyOfWaterMockMvc.perform(post("/api/body-of-waters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyOfWater)))
            .andExpect(status().isBadRequest());

        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBodyOfWaters() throws Exception {
        // Initialize the database
        bodyOfWaterRepository.saveAndFlush(bodyOfWater);

        // Get all the bodyOfWaterList
        restBodyOfWaterMockMvc.perform(get("/api/body-of-waters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyOfWater.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getBodyOfWater() throws Exception {
        // Initialize the database
        bodyOfWaterRepository.saveAndFlush(bodyOfWater);

        // Get the bodyOfWater
        restBodyOfWaterMockMvc.perform(get("/api/body-of-waters/{id}", bodyOfWater.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bodyOfWater.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBodyOfWater() throws Exception {
        // Get the bodyOfWater
        restBodyOfWaterMockMvc.perform(get("/api/body-of-waters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBodyOfWater() throws Exception {
        // Initialize the database
        bodyOfWaterRepository.saveAndFlush(bodyOfWater);
        int databaseSizeBeforeUpdate = bodyOfWaterRepository.findAll().size();

        // Update the bodyOfWater
        BodyOfWater updatedBodyOfWater = bodyOfWaterRepository.findOne(bodyOfWater.getId());
        updatedBodyOfWater
            .location(UPDATED_LOCATION)
            .size(UPDATED_SIZE)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restBodyOfWaterMockMvc.perform(put("/api/body-of-waters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBodyOfWater)))
            .andExpect(status().isOk());

        // Validate the BodyOfWater in the database
        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeUpdate);
        BodyOfWater testBodyOfWater = bodyOfWaterList.get(bodyOfWaterList.size() - 1);
        assertThat(testBodyOfWater.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testBodyOfWater.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testBodyOfWater.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testBodyOfWater.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testBodyOfWater.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testBodyOfWater.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBodyOfWater() throws Exception {
        int databaseSizeBeforeUpdate = bodyOfWaterRepository.findAll().size();

        // Create the BodyOfWater

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBodyOfWaterMockMvc.perform(put("/api/body-of-waters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyOfWater)))
            .andExpect(status().isCreated());

        // Validate the BodyOfWater in the database
        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBodyOfWater() throws Exception {
        // Initialize the database
        bodyOfWaterRepository.saveAndFlush(bodyOfWater);
        int databaseSizeBeforeDelete = bodyOfWaterRepository.findAll().size();

        // Get the bodyOfWater
        restBodyOfWaterMockMvc.perform(delete("/api/body-of-waters/{id}", bodyOfWater.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BodyOfWater> bodyOfWaterList = bodyOfWaterRepository.findAll();
        assertThat(bodyOfWaterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyOfWater.class);
        BodyOfWater bodyOfWater1 = new BodyOfWater();
        bodyOfWater1.setId(1L);
        BodyOfWater bodyOfWater2 = new BodyOfWater();
        bodyOfWater2.setId(bodyOfWater1.getId());
        assertThat(bodyOfWater1).isEqualTo(bodyOfWater2);
        bodyOfWater2.setId(2L);
        assertThat(bodyOfWater1).isNotEqualTo(bodyOfWater2);
        bodyOfWater1.setId(null);
        assertThat(bodyOfWater1).isNotEqualTo(bodyOfWater2);
    }
}
