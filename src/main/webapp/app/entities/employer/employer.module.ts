import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    EmployerService,
    EmployerPopupService,
    EmployerComponent,
    EmployerDetailComponent,
    EmployerDialogComponent,
    EmployerPopupComponent,
    EmployerDeletePopupComponent,
    EmployerDeleteDialogComponent,
    employerRoute,
    employerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employerRoute,
    ...employerPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmployerComponent,
        EmployerDetailComponent,
        EmployerDialogComponent,
        EmployerDeleteDialogComponent,
        EmployerPopupComponent,
        EmployerDeletePopupComponent,
    ],
    entryComponents: [
        EmployerComponent,
        EmployerDialogComponent,
        EmployerPopupComponent,
        EmployerDeleteDialogComponent,
        EmployerDeletePopupComponent,
    ],
    providers: [
        EmployerService,
        EmployerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEmployerModule {}
