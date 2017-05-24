package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.A;
import gov.ca.cwds.cals.rest.api.repository.ARepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AResource REST controller.
 *
 * @see AResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class AResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 0;
    private static final Integer UPDATED_COUNT = 1;

    @Autowired
    private ARepository aRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAMockMvc;

    private A a;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AResource aResource = new AResource(aRepository);
        this.restAMockMvc = MockMvcBuilders.standaloneSetup(aResource)
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
    public static A createEntity(EntityManager em) {
        A a = new A()
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT)
            .count(DEFAULT_COUNT);
        return a;
    }

    @Before
    public void initTest() {
        a = createEntity(em);
    }

    @Test
    @Transactional
    public void createA() throws Exception {
        int databaseSizeBeforeCreate = aRepository.findAll().size();

        // Create the A
        restAMockMvc.perform(post("/api/a-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isCreated());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeCreate + 1);
        A testA = aList.get(aList.size() - 1);
        assertThat(testA.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testA.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testA.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createAWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aRepository.findAll().size();

        // Create the A with an existing ID
        a.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAMockMvc.perform(post("/api/a-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAS() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);

        // Get all the aList
        restAMockMvc.perform(get("/api/a-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(a.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getA() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);

        // Get the a
        restAMockMvc.perform(get("/api/a-s/{id}", a.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(a.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingA() throws Exception {
        // Get the a
        restAMockMvc.perform(get("/api/a-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateA() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);
        int databaseSizeBeforeUpdate = aRepository.findAll().size();

        // Update the a
        A updatedA = aRepository.findOne(a.getId());
        updatedA
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .count(UPDATED_COUNT);

        restAMockMvc.perform(put("/api/a-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedA)))
            .andExpect(status().isOk());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
        A testA = aList.get(aList.size() - 1);
        assertThat(testA.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testA.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testA.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingA() throws Exception {
        int databaseSizeBeforeUpdate = aRepository.findAll().size();

        // Create the A

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAMockMvc.perform(put("/api/a-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isCreated());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteA() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);
        int databaseSizeBeforeDelete = aRepository.findAll().size();

        // Get the a
        restAMockMvc.perform(delete("/api/a-s/{id}", a.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(A.class);
    }
}
