import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    PersonLanguageService,
    PersonLanguagePopupService,
    PersonLanguageComponent,
    PersonLanguageDetailComponent,
    PersonLanguageDialogComponent,
    PersonLanguagePopupComponent,
    PersonLanguageDeletePopupComponent,
    PersonLanguageDeleteDialogComponent,
    personLanguageRoute,
    personLanguagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...personLanguageRoute,
    ...personLanguagePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonLanguageComponent,
        PersonLanguageDetailComponent,
        PersonLanguageDialogComponent,
        PersonLanguageDeleteDialogComponent,
        PersonLanguagePopupComponent,
        PersonLanguageDeletePopupComponent,
    ],
    entryComponents: [
        PersonLanguageComponent,
        PersonLanguageDialogComponent,
        PersonLanguagePopupComponent,
        PersonLanguageDeleteDialogComponent,
        PersonLanguageDeletePopupComponent,
    ],
    providers: [
        PersonLanguageService,
        PersonLanguagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPersonLanguageModule {}
