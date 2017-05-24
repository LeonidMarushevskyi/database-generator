import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    FacilityStatusService,
    FacilityStatusPopupService,
    FacilityStatusComponent,
    FacilityStatusDetailComponent,
    FacilityStatusDialogComponent,
    FacilityStatusPopupComponent,
    FacilityStatusDeletePopupComponent,
    FacilityStatusDeleteDialogComponent,
    facilityStatusRoute,
    facilityStatusPopupRoute,
} from './';

let ENTITY_STATES = [
    ...facilityStatusRoute,
    ...facilityStatusPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FacilityStatusComponent,
        FacilityStatusDetailComponent,
        FacilityStatusDialogComponent,
        FacilityStatusDeleteDialogComponent,
        FacilityStatusPopupComponent,
        FacilityStatusDeletePopupComponent,
    ],
    entryComponents: [
        FacilityStatusComponent,
        FacilityStatusDialogComponent,
        FacilityStatusPopupComponent,
        FacilityStatusDeleteDialogComponent,
        FacilityStatusDeletePopupComponent,
    ],
    providers: [
        FacilityStatusService,
        FacilityStatusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorFacilityStatusModule {}
