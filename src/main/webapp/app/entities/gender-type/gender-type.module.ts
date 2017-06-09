import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    GenderTypeService,
    GenderTypePopupService,
    GenderTypeComponent,
    GenderTypeDetailComponent,
    GenderTypeDialogComponent,
    GenderTypePopupComponent,
    GenderTypeDeletePopupComponent,
    GenderTypeDeleteDialogComponent,
    genderTypeRoute,
    genderTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...genderTypeRoute,
    ...genderTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GenderTypeComponent,
        GenderTypeDetailComponent,
        GenderTypeDialogComponent,
        GenderTypeDeleteDialogComponent,
        GenderTypePopupComponent,
        GenderTypeDeletePopupComponent,
    ],
    entryComponents: [
        GenderTypeComponent,
        GenderTypeDialogComponent,
        GenderTypePopupComponent,
        GenderTypeDeleteDialogComponent,
        GenderTypeDeletePopupComponent,
    ],
    providers: [
        GenderTypeService,
        GenderTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorGenderTypeModule {}
