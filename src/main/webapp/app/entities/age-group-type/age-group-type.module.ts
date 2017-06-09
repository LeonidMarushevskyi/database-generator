import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    AgeGroupTypeService,
    AgeGroupTypePopupService,
    AgeGroupTypeComponent,
    AgeGroupTypeDetailComponent,
    AgeGroupTypeDialogComponent,
    AgeGroupTypePopupComponent,
    AgeGroupTypeDeletePopupComponent,
    AgeGroupTypeDeleteDialogComponent,
    ageGroupTypeRoute,
    ageGroupTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...ageGroupTypeRoute,
    ...ageGroupTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AgeGroupTypeComponent,
        AgeGroupTypeDetailComponent,
        AgeGroupTypeDialogComponent,
        AgeGroupTypeDeleteDialogComponent,
        AgeGroupTypePopupComponent,
        AgeGroupTypeDeletePopupComponent,
    ],
    entryComponents: [
        AgeGroupTypeComponent,
        AgeGroupTypeDialogComponent,
        AgeGroupTypePopupComponent,
        AgeGroupTypeDeleteDialogComponent,
        AgeGroupTypeDeletePopupComponent,
    ],
    providers: [
        AgeGroupTypeService,
        AgeGroupTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorAgeGroupTypeModule {}
