import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    FacilityPhoneService,
    FacilityPhonePopupService,
    FacilityPhoneComponent,
    FacilityPhoneDetailComponent,
    FacilityPhoneDialogComponent,
    FacilityPhonePopupComponent,
    FacilityPhoneDeletePopupComponent,
    FacilityPhoneDeleteDialogComponent,
    facilityPhoneRoute,
    facilityPhonePopupRoute,
} from './';

const ENTITY_STATES = [
    ...facilityPhoneRoute,
    ...facilityPhonePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FacilityPhoneComponent,
        FacilityPhoneDetailComponent,
        FacilityPhoneDialogComponent,
        FacilityPhoneDeleteDialogComponent,
        FacilityPhonePopupComponent,
        FacilityPhoneDeletePopupComponent,
    ],
    entryComponents: [
        FacilityPhoneComponent,
        FacilityPhoneDialogComponent,
        FacilityPhonePopupComponent,
        FacilityPhoneDeleteDialogComponent,
        FacilityPhoneDeletePopupComponent,
    ],
    providers: [
        FacilityPhoneService,
        FacilityPhonePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorFacilityPhoneModule {}
