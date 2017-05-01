import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    FacilityAddressService,
    FacilityAddressPopupService,
    FacilityAddressComponent,
    FacilityAddressDetailComponent,
    FacilityAddressDialogComponent,
    FacilityAddressPopupComponent,
    FacilityAddressDeletePopupComponent,
    FacilityAddressDeleteDialogComponent,
    facilityAddressRoute,
    facilityAddressPopupRoute,
} from './';

let ENTITY_STATES = [
    ...facilityAddressRoute,
    ...facilityAddressPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FacilityAddressComponent,
        FacilityAddressDetailComponent,
        FacilityAddressDialogComponent,
        FacilityAddressDeleteDialogComponent,
        FacilityAddressPopupComponent,
        FacilityAddressDeletePopupComponent,
    ],
    entryComponents: [
        FacilityAddressComponent,
        FacilityAddressDialogComponent,
        FacilityAddressPopupComponent,
        FacilityAddressDeleteDialogComponent,
        FacilityAddressDeletePopupComponent,
    ],
    providers: [
        FacilityAddressService,
        FacilityAddressPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorFacilityAddressModule {}
