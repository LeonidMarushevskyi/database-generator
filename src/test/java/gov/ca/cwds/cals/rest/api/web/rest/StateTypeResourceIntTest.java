package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.StateType;
import gov.ca.cwds.cals.rest.api.repository.StateTypeRepository;
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
 * Test class for the StateTypeResource REST controller.
 *
 * @see StateTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class StateTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StateTypeRepository stateTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStateTypeMockMvc;

    private StateType stateType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StateTypeResource stateTypeResource = new StateTypeResource(stateTypeRepository);
        this.restStateTypeMockMvc = MockMvcBuilders.standaloneSetup(stateTypeResource)
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
    public static StateType createEntity(EntityManager em) {
        StateType stateType = new StateType()
            .name(DEFAULT_NAME);
        return stateType;
    }

    @Before
    public void initTest() {
        stateType = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateType() throws Exception {
        int databaseSizeBeforeCreate = stateTypeRepository.findAll().size();

        // Create the StateType
        restStateTypeMockMvc.perform(post("/api/state-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateType)))
            .andExpect(status().isCreated());

        // Validate the StateType in the database
        List<StateType> stateTypeList = stateTypeRepository.findAll();
        assertThat(stateTypeList).hasSize(databaseSizeBeforeCreate + 1);
        StateType testStateType = stateTypeList.get(stateTypeList.size() - 1);
        assertThat(testStateType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStateTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateTypeRepository.findAll().size();

        // Create the StateType with an existing ID
        stateType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateTypeMockMvc.perform(post("/api/state-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StateType> stateTypeList = stateTypeRepository.findAll();
        assertThat(stateTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateTypeRepository.findAll().size();
        // set the field null
        stateType.setName(null);

        // Create the StateType, which fails.

        restStateTypeMockMvc.perform(post("/api/state-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateType)))
            .andExpect(status().isBadRequest());

        List<StateType> stateTypeList = stateTypeRepository.findAll();
        assertThat(stateTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStateTypes() throws Exception {
        // Initialize the database
        stateTypeRepository.saveAndFlush(stateType);

        // Get all the stateTypeList
        restStateTypeMockMvc.perform(get("/api/state-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getStateType() throws Exception {
        // Initialize the database
        stateTypeRepository.saveAndFlush(stateType);

        // Get the stateType
        restStateTypeMockMvc.perform(get("/api/state-types/{id}", stateType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stateType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStateType() throws Exception {
        // Get the stateType
        restStateTypeMockMvc.perform(get("/api/state-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateType() throws Exception {
        // Initialize the database
        stateTypeRepository.saveAndFlush(stateType);
        int databaseSizeBeforeUpdate = stateTypeRepository.findAll().size();

        // Update the stateType
        StateType updatedStateType = stateTypeRepository.findOne(stateType.getId());
        updatedStateType
            .name(UPDATED_NAME);

        restStateTypeMockMvc.perform(put("/api/state-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStateType)))
            .andExpect(status().isOk());

        // Validate the StateType in the database
        List<StateType> stateTypeList = stateTypeRepository.findAll();
        assertThat(stateTypeList).hasSize(databaseSizeBeforeUpdate);
        StateType testStateType = stateTypeList.get(stateTypeList.size() - 1);
        assertThat(testStateType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStateType() throws Exception {
        int databaseSizeBeforeUpdate = stateTypeRepository.findAll().size();

        // Create the StateType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStateTypeMockMvc.perform(put("/api/state-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateType)))
            .andExpect(status().isCreated());

        // Validate the StateType in the database
        List<StateType> stateTypeList = stateTypeRepository.findAll();
        assertThat(stateTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStateType() throws Exception {
        // Initialize the database
        stateTypeRepository.saveAndFlush(stateType);
        int databaseSizeBeforeDelete = stateTypeRepository.findAll().size();

        // Get the stateType
        restStateTypeMockMvc.perform(delete("/api/state-types/{id}", stateType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StateType> stateTypeList = stateTypeRepository.findAll();
        assertThat(stateTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateType.class);
        StateType stateType1 = new StateType();
        stateType1.setId(1L);
        StateType stateType2 = new StateType();
        stateType2.setId(stateType1.getId());
        assertThat(stateType1).isEqualTo(stateType2);
        stateType2.setId(2L);
        assertThat(stateType1).isNotEqualTo(stateType2);
        stateType1.setId(null);
        assertThat(stateType1).isNotEqualTo(stateType2);
    }
}
