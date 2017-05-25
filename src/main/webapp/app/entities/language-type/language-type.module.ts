import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    LanguageTypeService,
    LanguageTypePopupService,
    LanguageTypeComponent,
    LanguageTypeDetailComponent,
    LanguageTypeDialogComponent,
    LanguageTypePopupComponent,
    LanguageTypeDeletePopupComponent,
    LanguageTypeDeleteDialogComponent,
    languageTypeRoute,
    languageTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...languageTypeRoute,
    ...languageTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LanguageTypeComponent,
        LanguageTypeDetailComponent,
        LanguageTypeDialogComponent,
        LanguageTypeDeleteDialogComponent,
        LanguageTypePopupComponent,
        LanguageTypeDeletePopupComponent,
    ],
    entryComponents: [
        LanguageTypeComponent,
        LanguageTypeDialogComponent,
        LanguageTypePopupComponent,
        LanguageTypeDeleteDialogComponent,
        LanguageTypeDeletePopupComponent,
    ],
    providers: [
        LanguageTypeService,
        LanguageTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorLanguageTypeModule {}
