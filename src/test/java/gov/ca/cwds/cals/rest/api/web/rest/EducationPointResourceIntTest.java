package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.EducationPoint;
import gov.ca.cwds.cals.rest.api.repository.EducationPointRepository;
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
 * Test class for the EducationPointResource REST controller.
 *
 * @see EducationPointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class EducationPointResourceIntTest {

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EducationPointRepository educationPointRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEducationPointMockMvc;

    private EducationPoint educationPoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EducationPointResource educationPointResource = new EducationPointResource(educationPointRepository);
        this.restEducationPointMockMvc = MockMvcBuilders.standaloneSetup(educationPointResource)
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
    public static EducationPoint createEntity(EntityManager em) {
        EducationPoint educationPoint = new EducationPoint()
            .degree(DEFAULT_DEGREE)
            .name(DEFAULT_NAME)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createDateTime(DEFAULT_CREATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME);
        return educationPoint;
    }

    @Before
    public void initTest() {
        educationPoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationPoint() throws Exception {
        int databaseSizeBeforeCreate = educationPointRepository.findAll().size();

        // Create the EducationPoint
        restEducationPointMockMvc.perform(post("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isCreated());

        // Validate the EducationPoint in the database
        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeCreate + 1);
        EducationPoint testEducationPoint = educationPointList.get(educationPointList.size() - 1);
        assertThat(testEducationPoint.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEducationPoint.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEducationPoint.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testEducationPoint.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testEducationPoint.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testEducationPoint.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createEducationPointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationPointRepository.findAll().size();

        // Create the EducationPoint with an existing ID
        educationPoint.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationPointMockMvc.perform(post("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationPointRepository.findAll().size();
        // set the field null
        educationPoint.setDegree(null);

        // Create the EducationPoint, which fails.

        restEducationPointMockMvc.perform(post("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isBadRequest());

        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationPointRepository.findAll().size();
        // set the field null
        educationPoint.setName(null);

        // Create the EducationPoint, which fails.

        restEducationPointMockMvc.perform(post("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isBadRequest());

        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationPointRepository.findAll().size();
        // set the field null
        educationPoint.setCreateUserId(null);

        // Create the EducationPoint, which fails.

        restEducationPointMockMvc.perform(post("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isBadRequest());

        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationPointRepository.findAll().size();
        // set the field null
        educationPoint.setCreateDateTime(null);

        // Create the EducationPoint, which fails.

        restEducationPointMockMvc.perform(post("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isBadRequest());

        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationPointRepository.findAll().size();
        // set the field null
        educationPoint.setUpdateUserId(null);

        // Create the EducationPoint, which fails.

        restEducationPointMockMvc.perform(post("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isBadRequest());

        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationPointRepository.findAll().size();
        // set the field null
        educationPoint.setUpdateDateTime(null);

        // Create the EducationPoint, which fails.

        restEducationPointMockMvc.perform(post("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isBadRequest());

        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEducationPoints() throws Exception {
        // Initialize the database
        educationPointRepository.saveAndFlush(educationPoint);

        // Get all the educationPointList
        restEducationPointMockMvc.perform(get("/api/education-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getEducationPoint() throws Exception {
        // Initialize the database
        educationPointRepository.saveAndFlush(educationPoint);

        // Get the educationPoint
        restEducationPointMockMvc.perform(get("/api/education-points/{id}", educationPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(educationPoint.getId().intValue()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.toString()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingEducationPoint() throws Exception {
        // Get the educationPoint
        restEducationPointMockMvc.perform(get("/api/education-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationPoint() throws Exception {
        // Initialize the database
        educationPointRepository.saveAndFlush(educationPoint);
        int databaseSizeBeforeUpdate = educationPointRepository.findAll().size();

        // Update the educationPoint
        EducationPoint updatedEducationPoint = educationPointRepository.findOne(educationPoint.getId());
        updatedEducationPoint
            .degree(UPDATED_DEGREE)
            .name(UPDATED_NAME)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createDateTime(UPDATED_CREATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME);

        restEducationPointMockMvc.perform(put("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEducationPoint)))
            .andExpect(status().isOk());

        // Validate the EducationPoint in the database
        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeUpdate);
        EducationPoint testEducationPoint = educationPointList.get(educationPointList.size() - 1);
        assertThat(testEducationPoint.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEducationPoint.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEducationPoint.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testEducationPoint.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testEducationPoint.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testEducationPoint.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationPoint() throws Exception {
        int databaseSizeBeforeUpdate = educationPointRepository.findAll().size();

        // Create the EducationPoint

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEducationPointMockMvc.perform(put("/api/education-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPoint)))
            .andExpect(status().isCreated());

        // Validate the EducationPoint in the database
        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEducationPoint() throws Exception {
        // Initialize the database
        educationPointRepository.saveAndFlush(educationPoint);
        int databaseSizeBeforeDelete = educationPointRepository.findAll().size();

        // Get the educationPoint
        restEducationPointMockMvc.perform(delete("/api/education-points/{id}", educationPoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EducationPoint> educationPointList = educationPointRepository.findAll();
        assertThat(educationPointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationPoint.class);
        EducationPoint educationPoint1 = new EducationPoint();
        educationPoint1.setId(1L);
        EducationPoint educationPoint2 = new EducationPoint();
        educationPoint2.setId(educationPoint1.getId());
        assertThat(educationPoint1).isEqualTo(educationPoint2);
        educationPoint2.setId(2L);
        assertThat(educationPoint1).isNotEqualTo(educationPoint2);
        educationPoint1.setId(null);
        assertThat(educationPoint1).isNotEqualTo(educationPoint2);
    }
}
