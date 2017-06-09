import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    EmploymentService,
    EmploymentPopupService,
    EmploymentComponent,
    EmploymentDetailComponent,
    EmploymentDialogComponent,
    EmploymentPopupComponent,
    EmploymentDeletePopupComponent,
    EmploymentDeleteDialogComponent,
    employmentRoute,
    employmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employmentRoute,
    ...employmentPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmploymentComponent,
        EmploymentDetailComponent,
        EmploymentDialogComponent,
        EmploymentDeleteDialogComponent,
        EmploymentPopupComponent,
        EmploymentDeletePopupComponent,
    ],
    entryComponents: [
        EmploymentComponent,
        EmploymentDialogComponent,
        EmploymentPopupComponent,
        EmploymentDeleteDialogComponent,
        EmploymentDeletePopupComponent,
    ],
    providers: [
        EmploymentService,
        EmploymentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEmploymentModule {}
