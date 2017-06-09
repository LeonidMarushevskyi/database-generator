import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    PhoneNumberTypeService,
    PhoneNumberTypePopupService,
    PhoneNumberTypeComponent,
    PhoneNumberTypeDetailComponent,
    PhoneNumberTypeDialogComponent,
    PhoneNumberTypePopupComponent,
    PhoneNumberTypeDeletePopupComponent,
    PhoneNumberTypeDeleteDialogComponent,
    phoneNumberTypeRoute,
    phoneNumberTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...phoneNumberTypeRoute,
    ...phoneNumberTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PhoneNumberTypeComponent,
        PhoneNumberTypeDetailComponent,
        PhoneNumberTypeDialogComponent,
        PhoneNumberTypeDeleteDialogComponent,
        PhoneNumberTypePopupComponent,
        PhoneNumberTypeDeletePopupComponent,
    ],
    entryComponents: [
        PhoneNumberTypeComponent,
        PhoneNumberTypeDialogComponent,
        PhoneNumberTypePopupComponent,
        PhoneNumberTypeDeleteDialogComponent,
        PhoneNumberTypeDeletePopupComponent,
    ],
    providers: [
        PhoneNumberTypeService,
        PhoneNumberTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPhoneNumberTypeModule {}
