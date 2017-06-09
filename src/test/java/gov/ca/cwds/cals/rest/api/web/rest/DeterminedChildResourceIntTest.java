package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.DeterminedChild;
import gov.ca.cwds.cals.rest.api.domain.CountyType;
import gov.ca.cwds.cals.rest.api.repository.DeterminedChildRepository;
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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeterminedChildResource REST controller.
 *
 * @see DeterminedChildResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class DeterminedChildResourceIntTest {

    private static final LocalDate DEFAULT_DATE_OF_PLACEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_PLACEMENT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DeterminedChildRepository determinedChildRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeterminedChildMockMvc;

    private DeterminedChild determinedChild;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeterminedChildResource determinedChildResource = new DeterminedChildResource(determinedChildRepository);
        this.restDeterminedChildMockMvc = MockMvcBuilders.standaloneSetup(determinedChildResource)
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
    public static DeterminedChild createEntity(EntityManager em) {
        DeterminedChild determinedChild = new DeterminedChild()
            .dateOfPlacement(DEFAULT_DATE_OF_PLACEMENT);
        // Add required entity
        CountyType countyOfJurisdiction = CountyTypeResourceIntTest.createEntity(em);
        em.persist(countyOfJurisdiction);
        em.flush();
        determinedChild.setCountyOfJurisdiction(countyOfJurisdiction);
        return determinedChild;
    }

    @Before
    public void initTest() {
        determinedChild = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeterminedChild() throws Exception {
        int databaseSizeBeforeCreate = determinedChildRepository.findAll().size();

        // Create the DeterminedChild
        restDeterminedChildMockMvc.perform(post("/api/determined-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(determinedChild)))
            .andExpect(status().isCreated());

        // Validate the DeterminedChild in the database
        List<DeterminedChild> determinedChildList = determinedChildRepository.findAll();
        assertThat(determinedChildList).hasSize(databaseSizeBeforeCreate + 1);
        DeterminedChild testDeterminedChild = determinedChildList.get(determinedChildList.size() - 1);
        assertThat(testDeterminedChild.getDateOfPlacement()).isEqualTo(DEFAULT_DATE_OF_PLACEMENT);
    }

    @Test
    @Transactional
    public void createDeterminedChildWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = determinedChildRepository.findAll().size();

        // Create the DeterminedChild with an existing ID
        determinedChild.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeterminedChildMockMvc.perform(post("/api/determined-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(determinedChild)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DeterminedChild> determinedChildList = determinedChildRepository.findAll();
        assertThat(determinedChildList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateOfPlacementIsRequired() throws Exception {
        int databaseSizeBeforeTest = determinedChildRepository.findAll().size();
        // set the field null
        determinedChild.setDateOfPlacement(null);

        // Create the DeterminedChild, which fails.

        restDeterminedChildMockMvc.perform(post("/api/determined-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(determinedChild)))
            .andExpect(status().isBadRequest());

        List<DeterminedChild> determinedChildList = determinedChildRepository.findAll();
        assertThat(determinedChildList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeterminedChildren() throws Exception {
        // Initialize the database
        determinedChildRepository.saveAndFlush(determinedChild);

        // Get all the determinedChildList
        restDeterminedChildMockMvc.perform(get("/api/determined-children?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(determinedChild.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfPlacement").value(hasItem(DEFAULT_DATE_OF_PLACEMENT.toString())));
    }

    @Test
    @Transactional
    public void getDeterminedChild() throws Exception {
        // Initialize the database
        determinedChildRepository.saveAndFlush(determinedChild);

        // Get the determinedChild
        restDeterminedChildMockMvc.perform(get("/api/determined-children/{id}", determinedChild.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(determinedChild.getId().intValue()))
            .andExpect(jsonPath("$.dateOfPlacement").value(DEFAULT_DATE_OF_PLACEMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeterminedChild() throws Exception {
        // Get the determinedChild
        restDeterminedChildMockMvc.perform(get("/api/determined-children/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeterminedChild() throws Exception {
        // Initialize the database
        determinedChildRepository.saveAndFlush(determinedChild);
        int databaseSizeBeforeUpdate = determinedChildRepository.findAll().size();

        // Update the determinedChild
        DeterminedChild updatedDeterminedChild = determinedChildRepository.findOne(determinedChild.getId());
        updatedDeterminedChild
            .dateOfPlacement(UPDATED_DATE_OF_PLACEMENT);

        restDeterminedChildMockMvc.perform(put("/api/determined-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeterminedChild)))
            .andExpect(status().isOk());

        // Validate the DeterminedChild in the database
        List<DeterminedChild> determinedChildList = determinedChildRepository.findAll();
        assertThat(determinedChildList).hasSize(databaseSizeBeforeUpdate);
        DeterminedChild testDeterminedChild = determinedChildList.get(determinedChildList.size() - 1);
        assertThat(testDeterminedChild.getDateOfPlacement()).isEqualTo(UPDATED_DATE_OF_PLACEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingDeterminedChild() throws Exception {
        int databaseSizeBeforeUpdate = determinedChildRepository.findAll().size();

        // Create the DeterminedChild

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeterminedChildMockMvc.perform(put("/api/determined-children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(determinedChild)))
            .andExpect(status().isCreated());

        // Validate the DeterminedChild in the database
        List<DeterminedChild> determinedChildList = determinedChildRepository.findAll();
        assertThat(determinedChildList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeterminedChild() throws Exception {
        // Initialize the database
        determinedChildRepository.saveAndFlush(determinedChild);
        int databaseSizeBeforeDelete = determinedChildRepository.findAll().size();

        // Get the determinedChild
        restDeterminedChildMockMvc.perform(delete("/api/determined-children/{id}", determinedChild.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeterminedChild> determinedChildList = determinedChildRepository.findAll();
        assertThat(determinedChildList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeterminedChild.class);
        DeterminedChild determinedChild1 = new DeterminedChild();
        determinedChild1.setId(1L);
        DeterminedChild determinedChild2 = new DeterminedChild();
        determinedChild2.setId(determinedChild1.getId());
        assertThat(determinedChild1).isEqualTo(determinedChild2);
        determinedChild2.setId(2L);
        assertThat(determinedChild1).isNotEqualTo(determinedChild2);
        determinedChild1.setId(null);
        assertThat(determinedChild1).isNotEqualTo(determinedChild2);
    }
}
