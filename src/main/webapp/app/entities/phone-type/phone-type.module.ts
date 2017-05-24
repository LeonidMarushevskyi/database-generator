import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    PhoneTypeService,
    PhoneTypePopupService,
    PhoneTypeComponent,
    PhoneTypeDetailComponent,
    PhoneTypeDialogComponent,
    PhoneTypePopupComponent,
    PhoneTypeDeletePopupComponent,
    PhoneTypeDeleteDialogComponent,
    phoneTypeRoute,
    phoneTypePopupRoute,
} from './';

let ENTITY_STATES = [
    ...phoneTypeRoute,
    ...phoneTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PhoneTypeComponent,
        PhoneTypeDetailComponent,
        PhoneTypeDialogComponent,
        PhoneTypeDeleteDialogComponent,
        PhoneTypePopupComponent,
        PhoneTypeDeletePopupComponent,
    ],
    entryComponents: [
        PhoneTypeComponent,
        PhoneTypeDialogComponent,
        PhoneTypePopupComponent,
        PhoneTypeDeleteDialogComponent,
        PhoneTypeDeletePopupComponent,
    ],
    providers: [
        PhoneTypeService,
        PhoneTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPhoneTypeModule {}
