import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    StateTypeService,
    StateTypePopupService,
    StateTypeComponent,
    StateTypeDetailComponent,
    StateTypeDialogComponent,
    StateTypePopupComponent,
    StateTypeDeletePopupComponent,
    StateTypeDeleteDialogComponent,
    stateTypeRoute,
    stateTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...stateTypeRoute,
    ...stateTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StateTypeComponent,
        StateTypeDetailComponent,
        StateTypeDialogComponent,
        StateTypeDeleteDialogComponent,
        StateTypePopupComponent,
        StateTypeDeletePopupComponent,
    ],
    entryComponents: [
        StateTypeComponent,
        StateTypeDialogComponent,
        StateTypePopupComponent,
        StateTypeDeleteDialogComponent,
        StateTypeDeletePopupComponent,
    ],
    providers: [
        StateTypeService,
        StateTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorStateTypeModule {}
