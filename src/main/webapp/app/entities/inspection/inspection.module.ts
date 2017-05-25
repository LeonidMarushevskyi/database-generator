import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    InspectionService,
    InspectionPopupService,
    InspectionComponent,
    InspectionDetailComponent,
    InspectionDialogComponent,
    InspectionPopupComponent,
    InspectionDeletePopupComponent,
    InspectionDeleteDialogComponent,
    inspectionRoute,
    inspectionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...inspectionRoute,
    ...inspectionPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InspectionComponent,
        InspectionDetailComponent,
        InspectionDialogComponent,
        InspectionDeleteDialogComponent,
        InspectionPopupComponent,
        InspectionDeletePopupComponent,
    ],
    entryComponents: [
        InspectionComponent,
        InspectionDialogComponent,
        InspectionPopupComponent,
        InspectionDeleteDialogComponent,
        InspectionDeletePopupComponent,
    ],
    providers: [
        InspectionService,
        InspectionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorInspectionModule {}
