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
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEntityModule {}
