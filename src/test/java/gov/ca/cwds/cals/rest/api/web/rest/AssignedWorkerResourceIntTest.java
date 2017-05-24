package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.AssignedWorker;
import gov.ca.cwds.cals.rest.api.repository.AssignedWorkerRepository;
import gov.ca.cwds.cals.rest.api.service.dto.AssignedWorkerDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.AssignedWorkerMapper;
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
 * Test class for the AssignedWorkerResource REST controller.
 *
 * @see AssignedWorkerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class AssignedWorkerResourceIntTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";

    @Autowired
    private AssignedWorkerRepository assignedWorkerRepository;

    @Autowired
    private AssignedWorkerMapper assignedWorkerMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssignedWorkerMockMvc;

    private AssignedWorker assignedWorker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssignedWorkerResource assignedWorkerResource = new AssignedWorkerResource(assignedWorkerRepository, assignedWorkerMapper);
        this.restAssignedWorkerMockMvc = MockMvcBuilders.standaloneSetup(assignedWorkerResource)
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
    public static AssignedWorker createEntity(EntityManager em) {
        AssignedWorker assignedWorker = new AssignedWorker()
            .code(DEFAULT_CODE);
        return assignedWorker;
    }

    @Before
    public void initTest() {
        assignedWorker = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignedWorker() throws Exception {
        int databaseSizeBeforeCreate = assignedWorkerRepository.findAll().size();

        // Create the AssignedWorker
        AssignedWorkerDTO assignedWorkerDTO = assignedWorkerMapper.assignedWorkerToAssignedWorkerDTO(assignedWorker);
        restAssignedWorkerMockMvc.perform(post("/api/assigned-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignedWorkerDTO)))
            .andExpect(status().isCreated());

        // Validate the AssignedWorker in the database
        List<AssignedWorker> assignedWorkerList = assignedWorkerRepository.findAll();
        assertThat(assignedWorkerList).hasSize(databaseSizeBeforeCreate + 1);
        AssignedWorker testAssignedWorker = assignedWorkerList.get(assignedWorkerList.size() - 1);
        assertThat(testAssignedWorker.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createAssignedWorkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignedWorkerRepository.findAll().size();

        // Create the AssignedWorker with an existing ID
        assignedWorker.setId(1L);
        AssignedWorkerDTO assignedWorkerDTO = assignedWorkerMapper.assignedWorkerToAssignedWorkerDTO(assignedWorker);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignedWorkerMockMvc.perform(post("/api/assigned-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignedWorkerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AssignedWorker> assignedWorkerList = assignedWorkerRepository.findAll();
        assertThat(assignedWorkerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignedWorkerRepository.findAll().size();
        // set the field null
        assignedWorker.setCode(null);

        // Create the AssignedWorker, which fails.
        AssignedWorkerDTO assignedWorkerDTO = assignedWorkerMapper.assignedWorkerToAssignedWorkerDTO(assignedWorker);

        restAssignedWorkerMockMvc.perform(post("/api/assigned-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignedWorkerDTO)))
            .andExpect(status().isBadRequest());

        List<AssignedWorker> assignedWorkerList = assignedWorkerRepository.findAll();
        assertThat(assignedWorkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssignedWorkers() throws Exception {
        // Initialize the database
        assignedWorkerRepository.saveAndFlush(assignedWorker);

        // Get all the assignedWorkerList
        restAssignedWorkerMockMvc.perform(get("/api/assigned-workers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignedWorker.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getAssignedWorker() throws Exception {
        // Initialize the database
        assignedWorkerRepository.saveAndFlush(assignedWorker);

        // Get the assignedWorker
        restAssignedWorkerMockMvc.perform(get("/api/assigned-workers/{id}", assignedWorker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assignedWorker.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssignedWorker() throws Exception {
        // Get the assignedWorker
        restAssignedWorkerMockMvc.perform(get("/api/assigned-workers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignedWorker() throws Exception {
        // Initialize the database
        assignedWorkerRepository.saveAndFlush(assignedWorker);
        int databaseSizeBeforeUpdate = assignedWorkerRepository.findAll().size();

        // Update the assignedWorker
        AssignedWorker updatedAssignedWorker = assignedWorkerRepository.findOne(assignedWorker.getId());
        updatedAssignedWorker
            .code(UPDATED_CODE);
        AssignedWorkerDTO assignedWorkerDTO = assignedWorkerMapper.assignedWorkerToAssignedWorkerDTO(updatedAssignedWorker);

        restAssignedWorkerMockMvc.perform(put("/api/assigned-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignedWorkerDTO)))
            .andExpect(status().isOk());

        // Validate the AssignedWorker in the database
        List<AssignedWorker> assignedWorkerList = assignedWorkerRepository.findAll();
        assertThat(assignedWorkerList).hasSize(databaseSizeBeforeUpdate);
        AssignedWorker testAssignedWorker = assignedWorkerList.get(assignedWorkerList.size() - 1);
        assertThat(testAssignedWorker.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignedWorker() throws Exception {
        int databaseSizeBeforeUpdate = assignedWorkerRepository.findAll().size();

        // Create the AssignedWorker
        AssignedWorkerDTO assignedWorkerDTO = assignedWorkerMapper.assignedWorkerToAssignedWorkerDTO(assignedWorker);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssignedWorkerMockMvc.perform(put("/api/assigned-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignedWorkerDTO)))
            .andExpect(status().isCreated());

        // Validate the AssignedWorker in the database
        List<AssignedWorker> assignedWorkerList = assignedWorkerRepository.findAll();
        assertThat(assignedWorkerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssignedWorker() throws Exception {
        // Initialize the database
        assignedWorkerRepository.saveAndFlush(assignedWorker);
        int databaseSizeBeforeDelete = assignedWorkerRepository.findAll().size();

        // Get the assignedWorker
        restAssignedWorkerMockMvc.perform(delete("/api/assigned-workers/{id}", assignedWorker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AssignedWorker> assignedWorkerList = assignedWorkerRepository.findAll();
        assertThat(assignedWorkerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignedWorker.class);
    }
}
