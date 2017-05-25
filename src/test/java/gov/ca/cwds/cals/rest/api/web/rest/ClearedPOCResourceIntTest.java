package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.ClearedPOC;
import gov.ca.cwds.cals.rest.api.repository.ClearedPOCRepository;
import gov.ca.cwds.cals.rest.api.service.dto.ClearedPOCDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.ClearedPOCMapper;
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
 * Test class for the ClearedPOCResource REST controller.
 *
 * @see ClearedPOCResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class ClearedPOCResourceIntTest {

    private static final ZonedDateTime DEFAULT_POCDUEDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_POCDUEDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_POCSECTIONVIOLATED = "AAAAAAAAAA";
    private static final String UPDATED_POCSECTIONVIOLATED = "BBBBBBBBBB";

    private static final String DEFAULT_POCCORRECTIONPLAN = "AAAAAAAAAA";
    private static final String UPDATED_POCCORRECTIONPLAN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_POCDATECLEARED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_POCDATECLEARED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_POCCOMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_POCCOMMENTS = "BBBBBBBBBB";

    @Autowired
    private ClearedPOCRepository clearedPOCRepository;

    @Autowired
    private ClearedPOCMapper clearedPOCMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClearedPOCMockMvc;

    private ClearedPOC clearedPOC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClearedPOCResource clearedPOCResource = new ClearedPOCResource(clearedPOCRepository, clearedPOCMapper);
        this.restClearedPOCMockMvc = MockMvcBuilders.standaloneSetup(clearedPOCResource)
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
    public static ClearedPOC createEntity(EntityManager em) {
        ClearedPOC clearedPOC = new ClearedPOC()
            .pocduedate(DEFAULT_POCDUEDATE)
            .pocsectionviolated(DEFAULT_POCSECTIONVIOLATED)
            .poccorrectionplan(DEFAULT_POCCORRECTIONPLAN)
            .pocdatecleared(DEFAULT_POCDATECLEARED)
            .poccomments(DEFAULT_POCCOMMENTS);
        return clearedPOC;
    }

    @Before
    public void initTest() {
        clearedPOC = createEntity(em);
    }

    @Test
    @Transactional
    public void createClearedPOC() throws Exception {
        int databaseSizeBeforeCreate = clearedPOCRepository.findAll().size();

        // Create the ClearedPOC
        ClearedPOCDTO clearedPOCDTO = clearedPOCMapper.toDto(clearedPOC);
        restClearedPOCMockMvc.perform(post("/api/cleared-pocs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clearedPOCDTO)))
            .andExpect(status().isCreated());

        // Validate the ClearedPOC in the database
        List<ClearedPOC> clearedPOCList = clearedPOCRepository.findAll();
        assertThat(clearedPOCList).hasSize(databaseSizeBeforeCreate + 1);
        ClearedPOC testClearedPOC = clearedPOCList.get(clearedPOCList.size() - 1);
        assertThat(testClearedPOC.getPocduedate()).isEqualTo(DEFAULT_POCDUEDATE);
        assertThat(testClearedPOC.getPocsectionviolated()).isEqualTo(DEFAULT_POCSECTIONVIOLATED);
        assertThat(testClearedPOC.getPoccorrectionplan()).isEqualTo(DEFAULT_POCCORRECTIONPLAN);
        assertThat(testClearedPOC.getPocdatecleared()).isEqualTo(DEFAULT_POCDATECLEARED);
        assertThat(testClearedPOC.getPoccomments()).isEqualTo(DEFAULT_POCCOMMENTS);
    }

    @Test
    @Transactional
    public void createClearedPOCWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clearedPOCRepository.findAll().size();

        // Create the ClearedPOC with an existing ID
        clearedPOC.setId(1L);
        ClearedPOCDTO clearedPOCDTO = clearedPOCMapper.toDto(clearedPOC);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClearedPOCMockMvc.perform(post("/api/cleared-pocs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clearedPOCDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClearedPOC> clearedPOCList = clearedPOCRepository.findAll();
        assertThat(clearedPOCList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClearedPOCS() throws Exception {
        // Initialize the database
        clearedPOCRepository.saveAndFlush(clearedPOC);

        // Get all the clearedPOCList
        restClearedPOCMockMvc.perform(get("/api/cleared-pocs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clearedPOC.getId().intValue())))
            .andExpect(jsonPath("$.[*].pocduedate").value(hasItem(sameInstant(DEFAULT_POCDUEDATE))))
            .andExpect(jsonPath("$.[*].pocsectionviolated").value(hasItem(DEFAULT_POCSECTIONVIOLATED.toString())))
            .andExpect(jsonPath("$.[*].poccorrectionplan").value(hasItem(DEFAULT_POCCORRECTIONPLAN.toString())))
            .andExpect(jsonPath("$.[*].pocdatecleared").value(hasItem(sameInstant(DEFAULT_POCDATECLEARED))))
            .andExpect(jsonPath("$.[*].poccomments").value(hasItem(DEFAULT_POCCOMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getClearedPOC() throws Exception {
        // Initialize the database
        clearedPOCRepository.saveAndFlush(clearedPOC);

        // Get the clearedPOC
        restClearedPOCMockMvc.perform(get("/api/cleared-pocs/{id}", clearedPOC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clearedPOC.getId().intValue()))
            .andExpect(jsonPath("$.pocduedate").value(sameInstant(DEFAULT_POCDUEDATE)))
            .andExpect(jsonPath("$.pocsectionviolated").value(DEFAULT_POCSECTIONVIOLATED.toString()))
            .andExpect(jsonPath("$.poccorrectionplan").value(DEFAULT_POCCORRECTIONPLAN.toString()))
            .andExpect(jsonPath("$.pocdatecleared").value(sameInstant(DEFAULT_POCDATECLEARED)))
            .andExpect(jsonPath("$.poccomments").value(DEFAULT_POCCOMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClearedPOC() throws Exception {
        // Get the clearedPOC
        restClearedPOCMockMvc.perform(get("/api/cleared-pocs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClearedPOC() throws Exception {
        // Initialize the database
        clearedPOCRepository.saveAndFlush(clearedPOC);
        int databaseSizeBeforeUpdate = clearedPOCRepository.findAll().size();

        // Update the clearedPOC
        ClearedPOC updatedClearedPOC = clearedPOCRepository.findOne(clearedPOC.getId());
        updatedClearedPOC
            .pocduedate(UPDATED_POCDUEDATE)
            .pocsectionviolated(UPDATED_POCSECTIONVIOLATED)
            .poccorrectionplan(UPDATED_POCCORRECTIONPLAN)
            .pocdatecleared(UPDATED_POCDATECLEARED)
            .poccomments(UPDATED_POCCOMMENTS);
        ClearedPOCDTO clearedPOCDTO = clearedPOCMapper.toDto(updatedClearedPOC);

        restClearedPOCMockMvc.perform(put("/api/cleared-pocs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clearedPOCDTO)))
            .andExpect(status().isOk());

        // Validate the ClearedPOC in the database
        List<ClearedPOC> clearedPOCList = clearedPOCRepository.findAll();
        assertThat(clearedPOCList).hasSize(databaseSizeBeforeUpdate);
        ClearedPOC testClearedPOC = clearedPOCList.get(clearedPOCList.size() - 1);
        assertThat(testClearedPOC.getPocduedate()).isEqualTo(UPDATED_POCDUEDATE);
        assertThat(testClearedPOC.getPocsectionviolated()).isEqualTo(UPDATED_POCSECTIONVIOLATED);
        assertThat(testClearedPOC.getPoccorrectionplan()).isEqualTo(UPDATED_POCCORRECTIONPLAN);
        assertThat(testClearedPOC.getPocdatecleared()).isEqualTo(UPDATED_POCDATECLEARED);
        assertThat(testClearedPOC.getPoccomments()).isEqualTo(UPDATED_POCCOMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingClearedPOC() throws Exception {
        int databaseSizeBeforeUpdate = clearedPOCRepository.findAll().size();

        // Create the ClearedPOC
        ClearedPOCDTO clearedPOCDTO = clearedPOCMapper.toDto(clearedPOC);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClearedPOCMockMvc.perform(put("/api/cleared-pocs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clearedPOCDTO)))
            .andExpect(status().isCreated());

        // Validate the ClearedPOC in the database
        List<ClearedPOC> clearedPOCList = clearedPOCRepository.findAll();
        assertThat(clearedPOCList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClearedPOC() throws Exception {
        // Initialize the database
        clearedPOCRepository.saveAndFlush(clearedPOC);
        int databaseSizeBeforeDelete = clearedPOCRepository.findAll().size();

        // Get the clearedPOC
        restClearedPOCMockMvc.perform(delete("/api/cleared-pocs/{id}", clearedPOC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClearedPOC> clearedPOCList = clearedPOCRepository.findAll();
        assertThat(clearedPOCList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClearedPOC.class);
        ClearedPOC clearedPOC1 = new ClearedPOC();
        clearedPOC1.setId(1L);
        ClearedPOC clearedPOC2 = new ClearedPOC();
        clearedPOC2.setId(clearedPOC1.getId());
        assertThat(clearedPOC1).isEqualTo(clearedPOC2);
        clearedPOC2.setId(2L);
        assertThat(clearedPOC1).isNotEqualTo(clearedPOC2);
        clearedPOC1.setId(null);
        assertThat(clearedPOC1).isNotEqualTo(clearedPOC2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClearedPOCDTO.class);
        ClearedPOCDTO clearedPOCDTO1 = new ClearedPOCDTO();
        clearedPOCDTO1.setId(1L);
        ClearedPOCDTO clearedPOCDTO2 = new ClearedPOCDTO();
        assertThat(clearedPOCDTO1).isNotEqualTo(clearedPOCDTO2);
        clearedPOCDTO2.setId(clearedPOCDTO1.getId());
        assertThat(clearedPOCDTO1).isEqualTo(clearedPOCDTO2);
        clearedPOCDTO2.setId(2L);
        assertThat(clearedPOCDTO1).isNotEqualTo(clearedPOCDTO2);
        clearedPOCDTO1.setId(null);
        assertThat(clearedPOCDTO1).isNotEqualTo(clearedPOCDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clearedPOCMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clearedPOCMapper.fromId(null)).isNull();
    }
}
