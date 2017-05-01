import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    PersonEthnicityService,
    PersonEthnicityPopupService,
    PersonEthnicityComponent,
    PersonEthnicityDetailComponent,
    PersonEthnicityDialogComponent,
    PersonEthnicityPopupComponent,
    PersonEthnicityDeletePopupComponent,
    PersonEthnicityDeleteDialogComponent,
    personEthnicityRoute,
    personEthnicityPopupRoute,
} from './';

let ENTITY_STATES = [
    ...personEthnicityRoute,
    ...personEthnicityPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonEthnicityComponent,
        PersonEthnicityDetailComponent,
        PersonEthnicityDialogComponent,
        PersonEthnicityDeleteDialogComponent,
        PersonEthnicityPopupComponent,
        PersonEthnicityDeletePopupComponent,
    ],
    entryComponents: [
        PersonEthnicityComponent,
        PersonEthnicityDialogComponent,
        PersonEthnicityPopupComponent,
        PersonEthnicityDeleteDialogComponent,
        PersonEthnicityDeletePopupComponent,
    ],
    providers: [
        PersonEthnicityService,
        PersonEthnicityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPersonEthnicityModule {}
