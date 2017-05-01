import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    AService,
    APopupService,
    AComponent,
    ADetailComponent,
    ADialogComponent,
    APopupComponent,
    ADeletePopupComponent,
    ADeleteDialogComponent,
    aRoute,
    aPopupRoute,
} from './';

let ENTITY_STATES = [
    ...aRoute,
    ...aPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AComponent,
        ADetailComponent,
        ADialogComponent,
        ADeleteDialogComponent,
        APopupComponent,
        ADeletePopupComponent,
    ],
    entryComponents: [
        AComponent,
        ADialogComponent,
        APopupComponent,
        ADeleteDialogComponent,
        ADeletePopupComponent,
    ],
    providers: [
        AService,
        APopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorAModule {}
