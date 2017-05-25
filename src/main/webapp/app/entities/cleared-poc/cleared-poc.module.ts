import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    ClearedPOCService,
    ClearedPOCPopupService,
    ClearedPOCComponent,
    ClearedPOCDetailComponent,
    ClearedPOCDialogComponent,
    ClearedPOCPopupComponent,
    ClearedPOCDeletePopupComponent,
    ClearedPOCDeleteDialogComponent,
    clearedPOCRoute,
    clearedPOCPopupRoute,
} from './';

const ENTITY_STATES = [
    ...clearedPOCRoute,
    ...clearedPOCPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClearedPOCComponent,
        ClearedPOCDetailComponent,
        ClearedPOCDialogComponent,
        ClearedPOCDeleteDialogComponent,
        ClearedPOCPopupComponent,
        ClearedPOCDeletePopupComponent,
    ],
    entryComponents: [
        ClearedPOCComponent,
        ClearedPOCDialogComponent,
        ClearedPOCPopupComponent,
        ClearedPOCDeleteDialogComponent,
        ClearedPOCDeletePopupComponent,
    ],
    providers: [
        ClearedPOCService,
        ClearedPOCPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorClearedPOCModule {}
