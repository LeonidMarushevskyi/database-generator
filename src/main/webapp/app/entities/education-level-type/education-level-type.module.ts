import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    EducationLevelTypeService,
    EducationLevelTypePopupService,
    EducationLevelTypeComponent,
    EducationLevelTypeDetailComponent,
    EducationLevelTypeDialogComponent,
    EducationLevelTypePopupComponent,
    EducationLevelTypeDeletePopupComponent,
    EducationLevelTypeDeleteDialogComponent,
    educationLevelTypeRoute,
    educationLevelTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...educationLevelTypeRoute,
    ...educationLevelTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EducationLevelTypeComponent,
        EducationLevelTypeDetailComponent,
        EducationLevelTypeDialogComponent,
        EducationLevelTypeDeleteDialogComponent,
        EducationLevelTypePopupComponent,
        EducationLevelTypeDeletePopupComponent,
    ],
    entryComponents: [
        EducationLevelTypeComponent,
        EducationLevelTypeDialogComponent,
        EducationLevelTypePopupComponent,
        EducationLevelTypeDeleteDialogComponent,
        EducationLevelTypeDeletePopupComponent,
    ],
    providers: [
        EducationLevelTypeService,
        EducationLevelTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEducationLevelTypeModule {}
