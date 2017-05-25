import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    FacilityChildService,
    FacilityChildPopupService,
    FacilityChildComponent,
    FacilityChildDetailComponent,
    FacilityChildDialogComponent,
    FacilityChildPopupComponent,
    FacilityChildDeletePopupComponent,
    FacilityChildDeleteDialogComponent,
    facilityChildRoute,
    facilityChildPopupRoute,
    FacilityChildResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...facilityChildRoute,
    ...facilityChildPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FacilityChildComponent,
        FacilityChildDetailComponent,
        FacilityChildDialogComponent,
        FacilityChildDeleteDialogComponent,
        FacilityChildPopupComponent,
        FacilityChildDeletePopupComponent,
    ],
    entryComponents: [
        FacilityChildComponent,
        FacilityChildDialogComponent,
        FacilityChildPopupComponent,
        FacilityChildDeleteDialogComponent,
        FacilityChildDeletePopupComponent,
    ],
    providers: [
        FacilityChildService,
        FacilityChildPopupService,
        FacilityChildResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorFacilityChildModule {}
