import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    PhoneNumberService,
    PhoneNumberPopupService,
    PhoneNumberComponent,
    PhoneNumberDetailComponent,
    PhoneNumberDialogComponent,
    PhoneNumberPopupComponent,
    PhoneNumberDeletePopupComponent,
    PhoneNumberDeleteDialogComponent,
    phoneNumberRoute,
    phoneNumberPopupRoute,
} from './';

const ENTITY_STATES = [
    ...phoneNumberRoute,
    ...phoneNumberPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PhoneNumberComponent,
        PhoneNumberDetailComponent,
        PhoneNumberDialogComponent,
        PhoneNumberDeleteDialogComponent,
        PhoneNumberPopupComponent,
        PhoneNumberDeletePopupComponent,
    ],
    entryComponents: [
        PhoneNumberComponent,
        PhoneNumberDialogComponent,
        PhoneNumberPopupComponent,
        PhoneNumberDeleteDialogComponent,
        PhoneNumberDeletePopupComponent,
    ],
    providers: [
        PhoneNumberService,
        PhoneNumberPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPhoneNumberModule {}
