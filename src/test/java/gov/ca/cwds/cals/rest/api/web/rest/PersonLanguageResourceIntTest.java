package gov.ca.cwds.cals.rest.api.web.rest;

import gov.ca.cwds.cals.rest.api.GeneratorApp;

import gov.ca.cwds.cals.rest.api.domain.PersonLanguage;
import gov.ca.cwds.cals.rest.api.repository.PersonLanguageRepository;
import gov.ca.cwds.cals.rest.api.service.dto.PersonLanguageDTO;
import gov.ca.cwds.cals.rest.api.service.mapper.PersonLanguageMapper;
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
 * Test class for the PersonLanguageResource REST controller.
 *
 * @see PersonLanguageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApp.class)
public class PersonLanguageResourceIntTest {

    @Autowired
    private PersonLanguageRepository personLanguageRepository;

    @Autowired
    private PersonLanguageMapper personLanguageMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonLanguageMockMvc;

    private PersonLanguage personLanguage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonLanguageResource personLanguageResource = new PersonLanguageResource(personLanguageRepository, personLanguageMapper);
        this.restPersonLanguageMockMvc = MockMvcBuilders.standaloneSetup(personLanguageResource)
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
    public static PersonLanguage createEntity(EntityManager em) {
        PersonLanguage personLanguage = new PersonLanguage();
        return personLanguage;
    }

    @Before
    public void initTest() {
        personLanguage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonLanguage() throws Exception {
        int databaseSizeBeforeCreate = personLanguageRepository.findAll().size();

        // Create the PersonLanguage
        PersonLanguageDTO personLanguageDTO = personLanguageMapper.personLanguageToPersonLanguageDTO(personLanguage);
        restPersonLanguageMockMvc.perform(post("/api/person-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personLanguageDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonLanguage in the database
        List<PersonLanguage> personLanguageList = personLanguageRepository.findAll();
        assertThat(personLanguageList).hasSize(databaseSizeBeforeCreate + 1);
        PersonLanguage testPersonLanguage = personLanguageList.get(personLanguageList.size() - 1);
    }

    @Test
    @Transactional
    public void createPersonLanguageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personLanguageRepository.findAll().size();

        // Create the PersonLanguage with an existing ID
        personLanguage.setId(1L);
        PersonLanguageDTO personLanguageDTO = personLanguageMapper.personLanguageToPersonLanguageDTO(personLanguage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonLanguageMockMvc.perform(post("/api/person-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personLanguageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonLanguage> personLanguageList = personLanguageRepository.findAll();
        assertThat(personLanguageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonLanguages() throws Exception {
        // Initialize the database
        personLanguageRepository.saveAndFlush(personLanguage);

        // Get all the personLanguageList
        restPersonLanguageMockMvc.perform(get("/api/person-languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personLanguage.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPersonLanguage() throws Exception {
        // Initialize the database
        personLanguageRepository.saveAndFlush(personLanguage);

        // Get the personLanguage
        restPersonLanguageMockMvc.perform(get("/api/person-languages/{id}", personLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personLanguage.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonLanguage() throws Exception {
        // Get the personLanguage
        restPersonLanguageMockMvc.perform(get("/api/person-languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonLanguage() throws Exception {
        // Initialize the database
        personLanguageRepository.saveAndFlush(personLanguage);
        int databaseSizeBeforeUpdate = personLanguageRepository.findAll().size();

        // Update the personLanguage
        PersonLanguage updatedPersonLanguage = personLanguageRepository.findOne(personLanguage.getId());
        PersonLanguageDTO personLanguageDTO = personLanguageMapper.personLanguageToPersonLanguageDTO(updatedPersonLanguage);

        restPersonLanguageMockMvc.perform(put("/api/person-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personLanguageDTO)))
            .andExpect(status().isOk());

        // Validate the PersonLanguage in the database
        List<PersonLanguage> personLanguageList = personLanguageRepository.findAll();
        assertThat(personLanguageList).hasSize(databaseSizeBeforeUpdate);
        PersonLanguage testPersonLanguage = personLanguageList.get(personLanguageList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonLanguage() throws Exception {
        int databaseSizeBeforeUpdate = personLanguageRepository.findAll().size();

        // Create the PersonLanguage
        PersonLanguageDTO personLanguageDTO = personLanguageMapper.personLanguageToPersonLanguageDTO(personLanguage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonLanguageMockMvc.perform(put("/api/person-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personLanguageDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonLanguage in the database
        List<PersonLanguage> personLanguageList = personLanguageRepository.findAll();
        assertThat(personLanguageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonLanguage() throws Exception {
        // Initialize the database
        personLanguageRepository.saveAndFlush(personLanguage);
        int databaseSizeBeforeDelete = personLanguageRepository.findAll().size();

        // Get the personLanguage
        restPersonLanguageMockMvc.perform(delete("/api/person-languages/{id}", personLanguage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonLanguage> personLanguageList = personLanguageRepository.findAll();
        assertThat(personLanguageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonLanguage.class);
    }
}
