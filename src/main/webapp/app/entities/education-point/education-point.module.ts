import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    EducationPointService,
    EducationPointPopupService,
    EducationPointComponent,
    EducationPointDetailComponent,
    EducationPointDialogComponent,
    EducationPointPopupComponent,
    EducationPointDeletePopupComponent,
    EducationPointDeleteDialogComponent,
    educationPointRoute,
    educationPointPopupRoute,
} from './';

const ENTITY_STATES = [
    ...educationPointRoute,
    ...educationPointPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EducationPointComponent,
        EducationPointDetailComponent,
        EducationPointDialogComponent,
        EducationPointDeleteDialogComponent,
        EducationPointPopupComponent,
        EducationPointDeletePopupComponent,
    ],
    entryComponents: [
        EducationPointComponent,
        EducationPointDialogComponent,
        EducationPointPopupComponent,
        EducationPointDeleteDialogComponent,
        EducationPointDeletePopupComponent,
    ],
    providers: [
        EducationPointService,
        EducationPointPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEducationPointModule {}
