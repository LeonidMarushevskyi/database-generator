import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    HouseholdAddressService,
    HouseholdAddressPopupService,
    HouseholdAddressComponent,
    HouseholdAddressDetailComponent,
    HouseholdAddressDialogComponent,
    HouseholdAddressPopupComponent,
    HouseholdAddressDeletePopupComponent,
    HouseholdAddressDeleteDialogComponent,
    householdAddressRoute,
    householdAddressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...householdAddressRoute,
    ...householdAddressPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HouseholdAddressComponent,
        HouseholdAddressDetailComponent,
        HouseholdAddressDialogComponent,
        HouseholdAddressDeleteDialogComponent,
        HouseholdAddressPopupComponent,
        HouseholdAddressDeletePopupComponent,
    ],
    entryComponents: [
        HouseholdAddressComponent,
        HouseholdAddressDialogComponent,
        HouseholdAddressPopupComponent,
        HouseholdAddressDeleteDialogComponent,
        HouseholdAddressDeletePopupComponent,
    ],
    providers: [
        HouseholdAddressService,
        HouseholdAddressPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorHouseholdAddressModule {}
