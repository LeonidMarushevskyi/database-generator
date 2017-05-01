import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GeneratorAModule } from './a/a.module';
import { GeneratorAddressModule } from './address/address.module';
import { GeneratorAddressTypeModule } from './address-type/address-type.module';
import { GeneratorFacilityModule } from './facility/facility.module';
import { GeneratorFacilityAddressModule } from './facility-address/facility-address.module';
import { GeneratorEthnicityTypeModule } from './ethnicity-type/ethnicity-type.module';
import { GeneratorFacilityChildModule } from './facility-child/facility-child.module';
import { GeneratorFacilityPhoneModule } from './facility-phone/facility-phone.module';
import { GeneratorLanguageTypeModule } from './language-type/language-type.module';
import { GeneratorRaceTypeModule } from './race-type/race-type.module';
import { GeneratorPersonAddressModule } from './person-address/person-address.module';
import { GeneratorPersonEthnicityModule } from './person-ethnicity/person-ethnicity.module';
import { GeneratorPersonLanguageModule } from './person-language/person-language.module';
import { GeneratorPersonPhoneModule } from './person-phone/person-phone.module';
import { GeneratorPersonRaceModule } from './person-race/person-race.module';
import { GeneratorPhoneModule } from './phone/phone.module';
import { GeneratorPhoneTypeModule } from './phone-type/phone-type.module';
import { GeneratorPersonModule } from './person/person.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GeneratorAModule,
        GeneratorAddressModule,
        GeneratorAddressTypeModule,
        GeneratorFacilityModule,
        GeneratorFacilityAddressModule,
        GeneratorEthnicityTypeModule,
        GeneratorFacilityChildModule,
        GeneratorFacilityPhoneModule,
        GeneratorLanguageTypeModule,
        GeneratorRaceTypeModule,
        GeneratorPersonAddressModule,
        GeneratorPersonEthnicityModule,
        GeneratorPersonLanguageModule,
        GeneratorPersonPhoneModule,
        GeneratorPersonRaceModule,
        GeneratorPhoneModule,
        GeneratorPhoneTypeModule,
        GeneratorPersonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEntityModule {}
