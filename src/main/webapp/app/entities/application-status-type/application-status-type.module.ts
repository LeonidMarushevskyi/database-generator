import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    ApplicationStatusTypeService,
    ApplicationStatusTypePopupService,
    ApplicationStatusTypeComponent,
    ApplicationStatusTypeDetailComponent,
    ApplicationStatusTypeDialogComponent,
    ApplicationStatusTypePopupComponent,
    ApplicationStatusTypeDeletePopupComponent,
    ApplicationStatusTypeDeleteDialogComponent,
    applicationStatusTypeRoute,
    applicationStatusTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...applicationStatusTypeRoute,
    ...applicationStatusTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ApplicationStatusTypeComponent,
        ApplicationStatusTypeDetailComponent,
        ApplicationStatusTypeDialogComponent,
        ApplicationStatusTypeDeleteDialogComponent,
        ApplicationStatusTypePopupComponent,
        ApplicationStatusTypeDeletePopupComponent,
    ],
    entryComponents: [
        ApplicationStatusTypeComponent,
        ApplicationStatusTypeDialogComponent,
        ApplicationStatusTypePopupComponent,
        ApplicationStatusTypeDeleteDialogComponent,
        ApplicationStatusTypeDeletePopupComponent,
    ],
    providers: [
        ApplicationStatusTypeService,
        ApplicationStatusTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorApplicationStatusTypeModule {}
