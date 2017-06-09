import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    EmailAddressService,
    EmailAddressPopupService,
    EmailAddressComponent,
    EmailAddressDetailComponent,
    EmailAddressDialogComponent,
    EmailAddressPopupComponent,
    EmailAddressDeletePopupComponent,
    EmailAddressDeleteDialogComponent,
    emailAddressRoute,
    emailAddressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...emailAddressRoute,
    ...emailAddressPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmailAddressComponent,
        EmailAddressDetailComponent,
        EmailAddressDialogComponent,
        EmailAddressDeleteDialogComponent,
        EmailAddressPopupComponent,
        EmailAddressDeletePopupComponent,
    ],
    entryComponents: [
        EmailAddressComponent,
        EmailAddressDialogComponent,
        EmailAddressPopupComponent,
        EmailAddressDeleteDialogComponent,
        EmailAddressDeletePopupComponent,
    ],
    providers: [
        EmailAddressService,
        EmailAddressPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEmailAddressModule {}
