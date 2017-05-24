package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.FacilityAddress;
import gov.ca.cwds.cals.rest.api.repository.FacilityAddressRepository;
import gov.ca.cwds.cals.rest.api.service.dto.FacilityAddressDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.FacilityAddressMapper;
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
 * Test class for the FacilityAddressResource REST controller.
 *
 * @see FacilityAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class FacilityAddressResourceIntTest {

    @Autowired
    private FacilityAddressRepository facilityAddressRepository;

    @Autowired
    private FacilityAddressMapper facilityAddressMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacilityAddressMockMvc;

    private FacilityAddress facilityAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityAddressResource facilityAddressResource = new FacilityAddressResource(facilityAddressRepository, facilityAddressMapper);
        this.restFacilityAddressMockMvc = MockMvcBuilders.standaloneSetup(facilityAddressResource)
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
    public static FacilityAddress createEntity(EntityManager em) {
        FacilityAddress facilityAddress = new FacilityAddress();
        return facilityAddress;
    }

    @Before
    public void initTest() {
        facilityAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityAddress() throws Exception {
        int databaseSizeBeforeCreate = facilityAddressRepository.findAll().size();

        // Create the FacilityAddress
        FacilityAddressDTO facilityAddressDTO = facilityAddressMapper.facilityAddressToFacilityAddressDTO(facilityAddress);
        restFacilityAddressMockMvc.perform(post("/api/facility-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityAddress in the database
        List<FacilityAddress> facilityAddressList = facilityAddressRepository.findAll();
        assertThat(facilityAddressList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityAddress testFacilityAddress = facilityAddressList.get(facilityAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void createFacilityAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityAddressRepository.findAll().size();

        // Create the FacilityAddress with an existing ID
        facilityAddress.setId(1L);
        FacilityAddressDTO facilityAddressDTO = facilityAddressMapper.facilityAddressToFacilityAddressDTO(facilityAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityAddressMockMvc.perform(post("/api/facility-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FacilityAddress> facilityAddressList = facilityAddressRepository.findAll();
        assertThat(facilityAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFacilityAddresses() throws Exception {
        // Initialize the database
        facilityAddressRepository.saveAndFlush(facilityAddress);

        // Get all the facilityAddressList
        restFacilityAddressMockMvc.perform(get("/api/facility-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityAddress.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFacilityAddress() throws Exception {
        // Initialize the database
        facilityAddressRepository.saveAndFlush(facilityAddress);

        // Get the facilityAddress
        restFacilityAddressMockMvc.perform(get("/api/facility-addresses/{id}", facilityAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facilityAddress.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityAddress() throws Exception {
        // Get the facilityAddress
        restFacilityAddressMockMvc.perform(get("/api/facility-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityAddress() throws Exception {
        // Initialize the database
        facilityAddressRepository.saveAndFlush(facilityAddress);
        int databaseSizeBeforeUpdate = facilityAddressRepository.findAll().size();

        // Update the facilityAddress
        FacilityAddress updatedFacilityAddress = facilityAddressRepository.findOne(facilityAddress.getId());
        FacilityAddressDTO facilityAddressDTO = facilityAddressMapper.facilityAddressToFacilityAddressDTO(updatedFacilityAddress);

        restFacilityAddressMockMvc.perform(put("/api/facility-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityAddressDTO)))
            .andExpect(status().isOk());

        // Validate the FacilityAddress in the database
        List<FacilityAddress> facilityAddressList = facilityAddressRepository.findAll();
        assertThat(facilityAddressList).hasSize(databaseSizeBeforeUpdate);
        FacilityAddress testFacilityAddress = facilityAddressList.get(facilityAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityAddress() throws Exception {
        int databaseSizeBeforeUpdate = facilityAddressRepository.findAll().size();

        // Create the FacilityAddress
        FacilityAddressDTO facilityAddressDTO = facilityAddressMapper.facilityAddressToFacilityAddressDTO(facilityAddress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacilityAddressMockMvc.perform(put("/api/facility-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facilityAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityAddress in the database
        List<FacilityAddress> facilityAddressList = facilityAddressRepository.findAll();
        assertThat(facilityAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacilityAddress() throws Exception {
        // Initialize the database
        facilityAddressRepository.saveAndFlush(facilityAddress);
        int databaseSizeBeforeDelete = facilityAddressRepository.findAll().size();

        // Get the facilityAddress
        restFacilityAddressMockMvc.perform(delete("/api/facility-addresses/{id}", facilityAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityAddress> facilityAddressList = facilityAddressRepository.findAll();
        assertThat(facilityAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityAddress.class);
    }
}
