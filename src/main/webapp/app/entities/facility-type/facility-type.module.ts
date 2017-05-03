import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    FacilityTypeService,
    FacilityTypePopupService,
    FacilityTypeComponent,
    FacilityTypeDetailComponent,
    FacilityTypeDialogComponent,
    FacilityTypePopupComponent,
    FacilityTypeDeletePopupComponent,
    FacilityTypeDeleteDialogComponent,
    facilityTypeRoute,
    facilityTypePopupRoute,
} from './';

let ENTITY_STATES = [
    ...facilityTypeRoute,
    ...facilityTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FacilityTypeComponent,
        FacilityTypeDetailComponent,
        FacilityTypeDialogComponent,
        FacilityTypeDeleteDialogComponent,
        FacilityTypePopupComponent,
        FacilityTypeDeletePopupComponent,
    ],
    entryComponents: [
        FacilityTypeComponent,
        FacilityTypeDialogComponent,
        FacilityTypePopupComponent,
        FacilityTypeDeleteDialogComponent,
        FacilityTypeDeletePopupComponent,
    ],
    providers: [
        FacilityTypeService,
        FacilityTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorFacilityTypeModule {}
