import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    PersonPhoneService,
    PersonPhonePopupService,
    PersonPhoneComponent,
    PersonPhoneDetailComponent,
    PersonPhoneDialogComponent,
    PersonPhonePopupComponent,
    PersonPhoneDeletePopupComponent,
    PersonPhoneDeleteDialogComponent,
    personPhoneRoute,
    personPhonePopupRoute,
} from './';

let ENTITY_STATES = [
    ...personPhoneRoute,
    ...personPhonePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonPhoneComponent,
        PersonPhoneDetailComponent,
        PersonPhoneDialogComponent,
        PersonPhoneDeleteDialogComponent,
        PersonPhonePopupComponent,
        PersonPhoneDeletePopupComponent,
    ],
    entryComponents: [
        PersonPhoneComponent,
        PersonPhoneDialogComponent,
        PersonPhonePopupComponent,
        PersonPhoneDeleteDialogComponent,
        PersonPhoneDeletePopupComponent,
    ],
    providers: [
        PersonPhoneService,
        PersonPhonePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPersonPhoneModule {}
