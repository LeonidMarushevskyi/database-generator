package gov.ca.cwds.cals.rest.api.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.AddressType.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Facility.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Facility.class.getName() + ".addresses", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.FacilityAddress.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.A.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Facility.class.getName() + ".phones", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Facility.class.getName() + ".children", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.EthnicityType.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.FacilityChild.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.FacilityPhone.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.LanguageType.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.RaceType.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.PersonAddress.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.PersonEthnicity.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.PersonLanguage.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.PersonPhone.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.PersonRace.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Phone.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.PhoneType.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Person.class.getName() + ".phones", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Person.class.getName() + ".addresses", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Person.class.getName() + ".languages", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.Person.class.getName() + ".races", jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.FacilityStatus.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.DistrictOffice.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.County.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.FacilityType.class.getName(), jcacheConfiguration);
            cm.createCache(gov.ca.cwds.cals.rest.api.domain.AssignedWorker.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
