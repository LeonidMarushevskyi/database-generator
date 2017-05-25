package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.Deficiency;
import gov.ca.cwds.cals.rest.api.repository.DeficiencyRepository;
import gov.ca.cwds.cals.rest.api.service.dto.DeficiencyDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.DeficiencyMapper;
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
 * Test class for the DeficiencyResource REST controller.
 *
 * @see DeficiencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class DeficiencyResourceIntTest {

    private static final String DEFAULT_DEFICIENCY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEFICIENCY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFICIENCY_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DEFICIENCY_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_POC_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_POC_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_FAC_SECTION_VIOLATED = "AAAAAAAAAA";
    private static final String UPDATED_FAC_SECTION_VIOLATED = "BBBBBBBBBB";

    private static final String DEFAULT_DEFICIENCY = "AAAAAAAAAA";
    private static final String UPDATED_DEFICIENCY = "BBBBBBBBBB";

    private static final String DEFAULT_CORRECTION_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_CORRECTION_PLAN = "BBBBBBBBBB";

    @Autowired
    private DeficiencyRepository deficiencyRepository;

    @Autowired
    private DeficiencyMapper deficiencyMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeficiencyMockMvc;

    private Deficiency deficiency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeficiencyResource deficiencyResource = new DeficiencyResource(deficiencyRepository, deficiencyMapper);
        this.restDeficiencyMockMvc = MockMvcBuilders.standaloneSetup(deficiencyResource)
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
    public static Deficiency createEntity(EntityManager em) {
        Deficiency deficiency = new Deficiency()
            .deficiencyType(DEFAULT_DEFICIENCY_TYPE)
            .deficiencyTypeDescription(DEFAULT_DEFICIENCY_TYPE_DESCRIPTION)
            .pocDate(DEFAULT_POC_DATE)
            .facSectionViolated(DEFAULT_FAC_SECTION_VIOLATED)
            .deficiency(DEFAULT_DEFICIENCY)
            .correctionPlan(DEFAULT_CORRECTION_PLAN);
        return deficiency;
    }

    @Before
    public void initTest() {
        deficiency = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeficiency() throws Exception {
        int databaseSizeBeforeCreate = deficiencyRepository.findAll().size();

        // Create the Deficiency
        DeficiencyDTO deficiencyDTO = deficiencyMapper.toDto(deficiency);
        restDeficiencyMockMvc.perform(post("/api/deficiencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deficiencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Deficiency in the database
        List<Deficiency> deficiencyList = deficiencyRepository.findAll();
        assertThat(deficiencyList).hasSize(databaseSizeBeforeCreate + 1);
        Deficiency testDeficiency = deficiencyList.get(deficiencyList.size() - 1);
        assertThat(testDeficiency.getDeficiencyType()).isEqualTo(DEFAULT_DEFICIENCY_TYPE);
        assertThat(testDeficiency.getDeficiencyTypeDescription()).isEqualTo(DEFAULT_DEFICIENCY_TYPE_DESCRIPTION);
        assertThat(testDeficiency.getPocDate()).isEqualTo(DEFAULT_POC_DATE);
        assertThat(testDeficiency.getFacSectionViolated()).isEqualTo(DEFAULT_FAC_SECTION_VIOLATED);
        assertThat(testDeficiency.getDeficiency()).isEqualTo(DEFAULT_DEFICIENCY);
        assertThat(testDeficiency.getCorrectionPlan()).isEqualTo(DEFAULT_CORRECTION_PLAN);
    }

    @Test
    @Transactional
    public void createDeficiencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deficiencyRepository.findAll().size();

        // Create the Deficiency with an existing ID
        deficiency.setId(1L);
        DeficiencyDTO deficiencyDTO = deficiencyMapper.toDto(deficiency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeficiencyMockMvc.perform(post("/api/deficiencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deficiencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Deficiency> deficiencyList = deficiencyRepository.findAll();
        assertThat(deficiencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeficiencies() throws Exception {
        // Initialize the database
        deficiencyRepository.saveAndFlush(deficiency);

        // Get all the deficiencyList
        restDeficiencyMockMvc.perform(get("/api/deficiencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deficiency.getId().intValue())))
            .andExpect(jsonPath("$.[*].deficiencyType").value(hasItem(DEFAULT_DEFICIENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deficiencyTypeDescription").value(hasItem(DEFAULT_DEFICIENCY_TYPE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].pocDate").value(hasItem(sameInstant(DEFAULT_POC_DATE))))
            .andExpect(jsonPath("$.[*].facSectionViolated").value(hasItem(DEFAULT_FAC_SECTION_VIOLATED.toString())))
            .andExpect(jsonPath("$.[*].deficiency").value(hasItem(DEFAULT_DEFICIENCY.toString())))
            .andExpect(jsonPath("$.[*].correctionPlan").value(hasItem(DEFAULT_CORRECTION_PLAN.toString())));
    }

    @Test
    @Transactional
    public void getDeficiency() throws Exception {
        // Initialize the database
        deficiencyRepository.saveAndFlush(deficiency);

        // Get the deficiency
        restDeficiencyMockMvc.perform(get("/api/deficiencies/{id}", deficiency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deficiency.getId().intValue()))
            .andExpect(jsonPath("$.deficiencyType").value(DEFAULT_DEFICIENCY_TYPE.toString()))
            .andExpect(jsonPath("$.deficiencyTypeDescription").value(DEFAULT_DEFICIENCY_TYPE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pocDate").value(sameInstant(DEFAULT_POC_DATE)))
            .andExpect(jsonPath("$.facSectionViolated").value(DEFAULT_FAC_SECTION_VIOLATED.toString()))
            .andExpect(jsonPath("$.deficiency").value(DEFAULT_DEFICIENCY.toString()))
            .andExpect(jsonPath("$.correctionPlan").value(DEFAULT_CORRECTION_PLAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeficiency() throws Exception {
        // Get the deficiency
        restDeficiencyMockMvc.perform(get("/api/deficiencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeficiency() throws Exception {
        // Initialize the database
        deficiencyRepository.saveAndFlush(deficiency);
        int databaseSizeBeforeUpdate = deficiencyRepository.findAll().size();

        // Update the deficiency
        Deficiency updatedDeficiency = deficiencyRepository.findOne(deficiency.getId());
        updatedDeficiency
            .deficiencyType(UPDATED_DEFICIENCY_TYPE)
            .deficiencyTypeDescription(UPDATED_DEFICIENCY_TYPE_DESCRIPTION)
            .pocDate(UPDATED_POC_DATE)
            .facSectionViolated(UPDATED_FAC_SECTION_VIOLATED)
            .deficiency(UPDATED_DEFICIENCY)
            .correctionPlan(UPDATED_CORRECTION_PLAN);
        DeficiencyDTO deficiencyDTO = deficiencyMapper.toDto(updatedDeficiency);

        restDeficiencyMockMvc.perform(put("/api/deficiencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deficiencyDTO)))
            .andExpect(status().isOk());

        // Validate the Deficiency in the database
        List<Deficiency> deficiencyList = deficiencyRepository.findAll();
        assertThat(deficiencyList).hasSize(databaseSizeBeforeUpdate);
        Deficiency testDeficiency = deficiencyList.get(deficiencyList.size() - 1);
        assertThat(testDeficiency.getDeficiencyType()).isEqualTo(UPDATED_DEFICIENCY_TYPE);
        assertThat(testDeficiency.getDeficiencyTypeDescription()).isEqualTo(UPDATED_DEFICIENCY_TYPE_DESCRIPTION);
        assertThat(testDeficiency.getPocDate()).isEqualTo(UPDATED_POC_DATE);
        assertThat(testDeficiency.getFacSectionViolated()).isEqualTo(UPDATED_FAC_SECTION_VIOLATED);
        assertThat(testDeficiency.getDeficiency()).isEqualTo(UPDATED_DEFICIENCY);
        assertThat(testDeficiency.getCorrectionPlan()).isEqualTo(UPDATED_CORRECTION_PLAN);
    }

    @Test
    @Transactional
    public void updateNonExistingDeficiency() throws Exception {
        int databaseSizeBeforeUpdate = deficiencyRepository.findAll().size();

        // Create the Deficiency
        DeficiencyDTO deficiencyDTO = deficiencyMapper.toDto(deficiency);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeficiencyMockMvc.perform(put("/api/deficiencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deficiencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Deficiency in the database
        List<Deficiency> deficiencyList = deficiencyRepository.findAll();
        assertThat(deficiencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeficiency() throws Exception {
        // Initialize the database
        deficiencyRepository.saveAndFlush(deficiency);
        int databaseSizeBeforeDelete = deficiencyRepository.findAll().size();

        // Get the deficiency
        restDeficiencyMockMvc.perform(delete("/api/deficiencies/{id}", deficiency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Deficiency> deficiencyList = deficiencyRepository.findAll();
        assertThat(deficiencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deficiency.class);
        Deficiency deficiency1 = new Deficiency();
        deficiency1.setId(1L);
        Deficiency deficiency2 = new Deficiency();
        deficiency2.setId(deficiency1.getId());
        assertThat(deficiency1).isEqualTo(deficiency2);
        deficiency2.setId(2L);
        assertThat(deficiency1).isNotEqualTo(deficiency2);
        deficiency1.setId(null);
        assertThat(deficiency1).isNotEqualTo(deficiency2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeficiencyDTO.class);
        DeficiencyDTO deficiencyDTO1 = new DeficiencyDTO();
        deficiencyDTO1.setId(1L);
        DeficiencyDTO deficiencyDTO2 = new DeficiencyDTO();
        assertThat(deficiencyDTO1).isNotEqualTo(deficiencyDTO2);
        deficiencyDTO2.setId(deficiencyDTO1.getId());
        assertThat(deficiencyDTO1).isEqualTo(deficiencyDTO2);
        deficiencyDTO2.setId(2L);
        assertThat(deficiencyDTO1).isNotEqualTo(deficiencyDTO2);
        deficiencyDTO1.setId(null);
        assertThat(deficiencyDTO1).isNotEqualTo(deficiencyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deficiencyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deficiencyMapper.fromId(null)).isNull();
    }
}
