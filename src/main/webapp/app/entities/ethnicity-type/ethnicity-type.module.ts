import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    EthnicityTypeService,
    EthnicityTypePopupService,
    EthnicityTypeComponent,
    EthnicityTypeDetailComponent,
    EthnicityTypeDialogComponent,
    EthnicityTypePopupComponent,
    EthnicityTypeDeletePopupComponent,
    EthnicityTypeDeleteDialogComponent,
    ethnicityTypeRoute,
    ethnicityTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...ethnicityTypeRoute,
    ...ethnicityTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EthnicityTypeComponent,
        EthnicityTypeDetailComponent,
        EthnicityTypeDialogComponent,
        EthnicityTypeDeleteDialogComponent,
        EthnicityTypePopupComponent,
        EthnicityTypeDeletePopupComponent,
    ],
    entryComponents: [
        EthnicityTypeComponent,
        EthnicityTypeDialogComponent,
        EthnicityTypePopupComponent,
        EthnicityTypeDeleteDialogComponent,
        EthnicityTypeDeletePopupComponent,
    ],
    providers: [
        EthnicityTypeService,
        EthnicityTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorEthnicityTypeModule {}
