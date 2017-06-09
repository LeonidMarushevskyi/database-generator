import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    ApplicantService,
    ApplicantPopupService,
    ApplicantComponent,
    ApplicantDetailComponent,
    ApplicantDialogComponent,
    ApplicantPopupComponent,
    ApplicantDeletePopupComponent,
    ApplicantDeleteDialogComponent,
    applicantRoute,
    applicantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...applicantRoute,
    ...applicantPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ApplicantComponent,
        ApplicantDetailComponent,
        ApplicantDialogComponent,
        ApplicantDeleteDialogComponent,
        ApplicantPopupComponent,
        ApplicantDeletePopupComponent,
    ],
    entryComponents: [
        ApplicantComponent,
        ApplicantDialogComponent,
        ApplicantPopupComponent,
        ApplicantDeleteDialogComponent,
        ApplicantDeletePopupComponent,
    ],
    providers: [
        ApplicantService,
        ApplicantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorApplicantModule {}
