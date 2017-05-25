import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    FacilityService,
    FacilityPopupService,
    FacilityComponent,
    FacilityDetailComponent,
    FacilityDialogComponent,
    FacilityPopupComponent,
    FacilityDeletePopupComponent,
    FacilityDeleteDialogComponent,
    facilityRoute,
    facilityPopupRoute,
    FacilityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...facilityRoute,
    ...facilityPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FacilityComponent,
        FacilityDetailComponent,
        FacilityDialogComponent,
        FacilityDeleteDialogComponent,
        FacilityPopupComponent,
        FacilityDeletePopupComponent,
    ],
    entryComponents: [
        FacilityComponent,
        FacilityDialogComponent,
        FacilityPopupComponent,
        FacilityDeleteDialogComponent,
        FacilityDeletePopupComponent,
    ],
    providers: [
        FacilityService,
        FacilityPopupService,
        FacilityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorFacilityModule {}
