import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GeneratorAModule } from './a/a.module';
import { GeneratorAddressTypeModule } from './address-type/address-type.module';
import { GeneratorFacilityModule } from './facility/facility.module';
import { GeneratorAddressModule } from './address/address.module';
import { GeneratorFacilityAddressModule } from './facility-address/facility-address.module';
import { GeneratorPersonRaceModule } from './person-race/person-race.module';
import { GeneratorEthnicityTypeModule } from './ethnicity-type/ethnicity-type.module';
import { GeneratorRaceTypeModule } from './race-type/race-type.module';
import { GeneratorPhoneTypeModule } from './phone-type/phone-type.module';
import { GeneratorFacilityChildModule } from './facility-child/facility-child.module';
import { GeneratorFacilityPhoneModule } from './facility-phone/facility-phone.module';
import { GeneratorPhoneModule } from './phone/phone.module';
import { GeneratorPersonPhoneModule } from './person-phone/person-phone.module';
import { GeneratorLanguageTypeModule } from './language-type/language-type.module';
import { GeneratorPersonModule } from './person/person.module';
import { GeneratorPersonAddressModule } from './person-address/person-address.module';
import { GeneratorPersonEthnicityModule } from './person-ethnicity/person-ethnicity.module';
import { GeneratorPersonLanguageModule } from './person-language/person-language.module';
import { GeneratorFacilityTypeModule } from './facility-type/facility-type.module';
import { GeneratorDistrictOfficeModule } from './district-office/district-office.module';
import { GeneratorCountyModule } from './county/county.module';
import { GeneratorFacilityStatusModule } from './facility-status/facility-status.module';
import { GeneratorAssignedWorkerModule } from './assigned-worker/assigned-worker.module';
import { GeneratorComplaintModule } from './complaint/complaint.module';
import { GeneratorInspectionModule } from './inspection/inspection.module';
import { GeneratorDeficiencyModule } from './deficiency/deficiency.module';
import { GeneratorClearedPOCModule } from './cleared-poc/cleared-poc.module';
import { GeneratorApplicationModule } from './application/application.module';
import { GeneratorDeterminedChildModule } from './determined-child/determined-child.module';
import { GeneratorApplicationStatusTypeModule } from './application-status-type/application-status-type.module';
import { GeneratorLicensureHistoryModule } from './licensure-history/licensure-history.module';
import { GeneratorHouseholdModule } from './household/household.module';
import { GeneratorHouseholdAdultModule } from './household-adult/household-adult.module';
import { GeneratorApplicantModule } from './applicant/applicant.module';
import { GeneratorCriminalRecordModule } from './criminal-record/criminal-record.module';
import { GeneratorCountyTypeModule } from './county-type/county-type.module';
import { GeneratorStateTypeModule } from './state-type/state-type.module';
import { GeneratorPersonPreviousNameModule } from './person-previous-name/person-previous-name.module';
import { GeneratorEducationLevelTypeModule } from './education-level-type/education-level-type.module';
import { GeneratorEducationPointModule } from './education-point/education-point.module';
import { GeneratorGenderTypeModule } from './gender-type/gender-type.module';
import { GeneratorEmailAddressModule } from './email-address/email-address.module';
import { GeneratorPhoneNumberModule } from './phone-number/phone-number.module';
import { GeneratorPhoneNumberTypeModule } from './phone-number-type/phone-number-type.module';
import { GeneratorEmployerModule } from './employer/employer.module';
import { GeneratorEmploymentModule } from './employment/employment.module';
import { GeneratorPosessionTypeModule } from './posession-type/posession-type.module';
import { GeneratorHouseholdAddressModule } from './household-address/household-address.module';
import { GeneratorBodyOfWaterModule } from './body-of-water/body-of-water.module';
import { GeneratorRelationshipTypeModule } from './relationship-type/relationship-type.module';
import { GeneratorRelationshipModule } from './relationship/relationship.module';
import { GeneratorAppRelHistoryModule } from './app-rel-history/app-rel-history.module';
import { GeneratorRelationshipEventModule } from './relationship-event/relationship-event.module';
import { GeneratorRelationshipEventTypeModule } from './relationship-event-type/relationship-event-type.module';
import { GeneratorHouseholdChildModule } from './household-child/household-child.module';
import { GeneratorAgeGroupTypeModule } from './age-group-type/age-group-type.module';
import { GeneratorSiblingGroupTypeModule } from './sibling-group-type/sibling-group-type.module';
import { GeneratorChildPreferencesModule } from './child-preferences/child-preferences.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GeneratorAModule,
        GeneratorAddressTypeModule,
        GeneratorFacilityModule,
        GeneratorAddressModule,
        GeneratorFacilityAddressModule,
        GeneratorPersonRaceModule,
        GeneratorEthnicityTypeModule,
        GeneratorRaceTypeModule,
        GeneratorPhoneTypeModule,
        GeneratorFacilityChildModule,
        GeneratorFacilityPhoneModule,
        GeneratorPhoneModule,
        GeneratorPersonPhoneModule,
        GeneratorLanguageTypeModule,
        GeneratorPersonModule,
        GeneratorPersonAddressModule,
        GeneratorPersonEthnicityModule,
        GeneratorPersonLanguageModule,
        GeneratorFacilityTypeModule,
        GeneratorDistrictOfficeModule,
        GeneratorCountyModule,
        GeneratorFacilityStatusModule,
        GeneratorAssignedWorkerModule,
        GeneratorComplaintModule,
        GeneratorInspectionModule,
        GeneratorDeficiencyModule,
        GeneratorClearedPOCModule,
        GeneratorApplicationModule,
        GeneratorDeterminedChildModule,
        GeneratorApplicationStatusTypeModule,
        GeneratorLicensureHistoryModule,
        GeneratorHouseholdModule,
        GeneratorHouseholdAdultModule,
        GeneratorApplicantModule,
        GeneratorCriminalRecordModule,
        GeneratorCountyTypeModule,
        GeneratorStateTypeModule,
        GeneratorPersonPreviousNameModule,
        GeneratorEducationLevelTypeModule,
        GeneratorEducationPointModule,
        GeneratorGenderTypeModule,
        GeneratorEmailAddressModule,
        GeneratorPhoneNumberModule,
        GeneratorPhoneNumberTypeModule,
        GeneratorEmployerModule,
        GeneratorEmploymentModule,
        GeneratorPosessionTypeModule,
        GeneratorHouseholdAddressModule,
        GeneratorBodyOfWaterModule,
        GeneratorRelationshipTypeModule,
        GeneratorRelationshipModule,
        GeneratorAppRelHistoryModule,
        GeneratorRelationshipEventModule,
        GeneratorRelationshipEventTypeModule,
        GeneratorHouseholdChildModule,
        GeneratorAgeGroupTypeModule,
        GeneratorSiblingGroupTypeModule,
        GeneratorChildPreferencesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEntityModule {}
