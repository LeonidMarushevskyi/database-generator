package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.Inspection;
import gov.ca.cwds.cals.rest.api.repository.InspectionRepository;
import gov.ca.cwds.cals.rest.api.service.dto.InspectionDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.InspectionMapper;
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
 * Test class for the InspectionResource REST controller.
 *
 * @see InspectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class InspectionResourceIntTest {

    private static final LocalDate DEFAULT_REPRESENTATIVE_SIGNATURE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPRESENTATIVE_SIGNATURE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FORM_809_PRINT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FORM_809_PRINT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private InspectionMapper inspectionMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInspectionMockMvc;

    private Inspection inspection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InspectionResource inspectionResource = new InspectionResource(inspectionRepository, inspectionMapper);
        this.restInspectionMockMvc = MockMvcBuilders.standaloneSetup(inspectionResource)
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
    public static Inspection createEntity(EntityManager em) {
        Inspection inspection = new Inspection()
            .representativeSignatureDate(DEFAULT_REPRESENTATIVE_SIGNATURE_DATE)
            .form809PrintDate(DEFAULT_FORM_809_PRINT_DATE);
        return inspection;
    }

    @Before
    public void initTest() {
        inspection = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspection() throws Exception {
        int databaseSizeBeforeCreate = inspectionRepository.findAll().size();

        // Create the Inspection
        InspectionDTO inspectionDTO = inspectionMapper.toDto(inspection);
        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeCreate + 1);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getRepresentativeSignatureDate()).isEqualTo(DEFAULT_REPRESENTATIVE_SIGNATURE_DATE);
        assertThat(testInspection.getForm809PrintDate()).isEqualTo(DEFAULT_FORM_809_PRINT_DATE);
    }

    @Test
    @Transactional
    public void createInspectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspectionRepository.findAll().size();

        // Create the Inspection with an existing ID
        inspection.setId(1L);
        InspectionDTO inspectionDTO = inspectionMapper.toDto(inspection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInspections() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        // Get all the inspectionList
        restInspectionMockMvc.perform(get("/api/inspections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspection.getId().intValue())))
            .andExpect(jsonPath("$.[*].representativeSignatureDate").value(hasItem(DEFAULT_REPRESENTATIVE_SIGNATURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].form809PrintDate").value(hasItem(DEFAULT_FORM_809_PRINT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInspection() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        // Get the inspection
        restInspectionMockMvc.perform(get("/api/inspections/{id}", inspection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inspection.getId().intValue()))
            .andExpect(jsonPath("$.representativeSignatureDate").value(DEFAULT_REPRESENTATIVE_SIGNATURE_DATE.toString()))
            .andExpect(jsonPath("$.form809PrintDate").value(DEFAULT_FORM_809_PRINT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInspection() throws Exception {
        // Get the inspection
        restInspectionMockMvc.perform(get("/api/inspections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspection() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Update the inspection
        Inspection updatedInspection = inspectionRepository.findOne(inspection.getId());
        updatedInspection
            .representativeSignatureDate(UPDATED_REPRESENTATIVE_SIGNATURE_DATE)
            .form809PrintDate(UPDATED_FORM_809_PRINT_DATE);
        InspectionDTO inspectionDTO = inspectionMapper.toDto(updatedInspection);

        restInspectionMockMvc.perform(put("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isOk());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getRepresentativeSignatureDate()).isEqualTo(UPDATED_REPRESENTATIVE_SIGNATURE_DATE);
        assertThat(testInspection.getForm809PrintDate()).isEqualTo(UPDATED_FORM_809_PRINT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Create the Inspection
        InspectionDTO inspectionDTO = inspectionMapper.toDto(inspection);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInspectionMockMvc.perform(put("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInspection() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);
        int databaseSizeBeforeDelete = inspectionRepository.findAll().size();

        // Get the inspection
        restInspectionMockMvc.perform(delete("/api/inspections/{id}", inspection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspection.class);
        Inspection inspection1 = new Inspection();
        inspection1.setId(1L);
        Inspection inspection2 = new Inspection();
        inspection2.setId(inspection1.getId());
        assertThat(inspection1).isEqualTo(inspection2);
        inspection2.setId(2L);
        assertThat(inspection1).isNotEqualTo(inspection2);
        inspection1.setId(null);
        assertThat(inspection1).isNotEqualTo(inspection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionDTO.class);
        InspectionDTO inspectionDTO1 = new InspectionDTO();
        inspectionDTO1.setId(1L);
        InspectionDTO inspectionDTO2 = new InspectionDTO();
        assertThat(inspectionDTO1).isNotEqualTo(inspectionDTO2);
        inspectionDTO2.setId(inspectionDTO1.getId());
        assertThat(inspectionDTO1).isEqualTo(inspectionDTO2);
        inspectionDTO2.setId(2L);
        assertThat(inspectionDTO1).isNotEqualTo(inspectionDTO2);
        inspectionDTO1.setId(null);
        assertThat(inspectionDTO1).isNotEqualTo(inspectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inspectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inspectionMapper.fromId(null)).isNull();
    }
}
