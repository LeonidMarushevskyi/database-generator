package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.Complaint;
import gov.ca.cwds.cals.rest.api.repository.ComplaintRepository;
import gov.ca.cwds.cals.rest.api.service.dto.ComplaintDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.ComplaintMapper;
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
 * Test class for the ComplaintResource REST controller.
 *
 * @see ComplaintResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class ComplaintResourceIntTest {

    private static final LocalDate DEFAULT_COMPLAINT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPLAINT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTROL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY_LEVEL = 1;
    private static final Integer UPDATED_PRIORITY_LEVEL = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_APPROVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVAL_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ComplaintMapper complaintMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComplaintMockMvc;

    private Complaint complaint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComplaintResource complaintResource = new ComplaintResource(complaintRepository, complaintMapper);
        this.restComplaintMockMvc = MockMvcBuilders.standaloneSetup(complaintResource)
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
    public static Complaint createEntity(EntityManager em) {
        Complaint complaint = new Complaint()
            .complaintDate(DEFAULT_COMPLAINT_DATE)
            .controlNumber(DEFAULT_CONTROL_NUMBER)
            .priorityLevel(DEFAULT_PRIORITY_LEVEL)
            .status(DEFAULT_STATUS)
            .approvalDate(DEFAULT_APPROVAL_DATE);
        return complaint;
    }

    @Before
    public void initTest() {
        complaint = createEntity(em);
    }

    @Test
    @Transactional
    public void createComplaint() throws Exception {
        int databaseSizeBeforeCreate = complaintRepository.findAll().size();

        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);
        restComplaintMockMvc.perform(post("/api/complaints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complaintDTO)))
            .andExpect(status().isCreated());

        // Validate the Complaint in the database
        List<Complaint> complaintList = complaintRepository.findAll();
        assertThat(complaintList).hasSize(databaseSizeBeforeCreate + 1);
        Complaint testComplaint = complaintList.get(complaintList.size() - 1);
        assertThat(testComplaint.getComplaintDate()).isEqualTo(DEFAULT_COMPLAINT_DATE);
        assertThat(testComplaint.getControlNumber()).isEqualTo(DEFAULT_CONTROL_NUMBER);
        assertThat(testComplaint.getPriorityLevel()).isEqualTo(DEFAULT_PRIORITY_LEVEL);
        assertThat(testComplaint.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testComplaint.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
    }

    @Test
    @Transactional
    public void createComplaintWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = complaintRepository.findAll().size();

        // Create the Complaint with an existing ID
        complaint.setId(1L);
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplaintMockMvc.perform(post("/api/complaints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complaintDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Complaint> complaintList = complaintRepository.findAll();
        assertThat(complaintList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComplaints() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        // Get all the complaintList
        restComplaintMockMvc.perform(get("/api/complaints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complaint.getId().intValue())))
            .andExpect(jsonPath("$.[*].complaintDate").value(hasItem(DEFAULT_COMPLAINT_DATE.toString())))
            .andExpect(jsonPath("$.[*].controlNumber").value(hasItem(DEFAULT_CONTROL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].priorityLevel").value(hasItem(DEFAULT_PRIORITY_LEVEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())));
    }

    @Test
    @Transactional
    public void getComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        // Get the complaint
        restComplaintMockMvc.perform(get("/api/complaints/{id}", complaint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(complaint.getId().intValue()))
            .andExpect(jsonPath("$.complaintDate").value(DEFAULT_COMPLAINT_DATE.toString()))
            .andExpect(jsonPath("$.controlNumber").value(DEFAULT_CONTROL_NUMBER.toString()))
            .andExpect(jsonPath("$.priorityLevel").value(DEFAULT_PRIORITY_LEVEL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComplaint() throws Exception {
        // Get the complaint
        restComplaintMockMvc.perform(get("/api/complaints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);
        int databaseSizeBeforeUpdate = complaintRepository.findAll().size();

        // Update the complaint
        Complaint updatedComplaint = complaintRepository.findOne(complaint.getId());
        updatedComplaint
            .complaintDate(UPDATED_COMPLAINT_DATE)
            .controlNumber(UPDATED_CONTROL_NUMBER)
            .priorityLevel(UPDATED_PRIORITY_LEVEL)
            .status(UPDATED_STATUS)
            .approvalDate(UPDATED_APPROVAL_DATE);
        ComplaintDTO complaintDTO = complaintMapper.toDto(updatedComplaint);

        restComplaintMockMvc.perform(put("/api/complaints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complaintDTO)))
            .andExpect(status().isOk());

        // Validate the Complaint in the database
        List<Complaint> complaintList = complaintRepository.findAll();
        assertThat(complaintList).hasSize(databaseSizeBeforeUpdate);
        Complaint testComplaint = complaintList.get(complaintList.size() - 1);
        assertThat(testComplaint.getComplaintDate()).isEqualTo(UPDATED_COMPLAINT_DATE);
        assertThat(testComplaint.getControlNumber()).isEqualTo(UPDATED_CONTROL_NUMBER);
        assertThat(testComplaint.getPriorityLevel()).isEqualTo(UPDATED_PRIORITY_LEVEL);
        assertThat(testComplaint.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testComplaint.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingComplaint() throws Exception {
        int databaseSizeBeforeUpdate = complaintRepository.findAll().size();

        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComplaintMockMvc.perform(put("/api/complaints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complaintDTO)))
            .andExpect(status().isCreated());

        // Validate the Complaint in the database
        List<Complaint> complaintList = complaintRepository.findAll();
        assertThat(complaintList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);
        int databaseSizeBeforeDelete = complaintRepository.findAll().size();

        // Get the complaint
        restComplaintMockMvc.perform(delete("/api/complaints/{id}", complaint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Complaint> complaintList = complaintRepository.findAll();
        assertThat(complaintList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Complaint.class);
        Complaint complaint1 = new Complaint();
        complaint1.setId(1L);
        Complaint complaint2 = new Complaint();
        complaint2.setId(complaint1.getId());
        assertThat(complaint1).isEqualTo(complaint2);
        complaint2.setId(2L);
        assertThat(complaint1).isNotEqualTo(complaint2);
        complaint1.setId(null);
        assertThat(complaint1).isNotEqualTo(complaint2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplaintDTO.class);
        ComplaintDTO complaintDTO1 = new ComplaintDTO();
        complaintDTO1.setId(1L);
        ComplaintDTO complaintDTO2 = new ComplaintDTO();
        assertThat(complaintDTO1).isNotEqualTo(complaintDTO2);
        complaintDTO2.setId(complaintDTO1.getId());
        assertThat(complaintDTO1).isEqualTo(complaintDTO2);
        complaintDTO2.setId(2L);
        assertThat(complaintDTO1).isNotEqualTo(complaintDTO2);
        complaintDTO1.setId(null);
        assertThat(complaintDTO1).isNotEqualTo(complaintDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(complaintMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(complaintMapper.fromId(null)).isNull();
    }
}
