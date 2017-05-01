import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GeneratorAModule } from './a/a.module';
import { GeneratorAddressModule } from './address/address.module';
import { GeneratorAddressTypeModule } from './address-type/address-type.module';
import { GeneratorFacilityModule } from './facility/facility.module';
import { GeneratorFacilityAddressModule } from './facility-address/facility-address.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GeneratorAModule,
        GeneratorAddressModule,
        GeneratorAddressTypeModule,
        GeneratorFacilityModule,
        GeneratorFacilityAddressModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEntityModule {}
