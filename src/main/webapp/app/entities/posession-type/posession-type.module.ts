import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    PosessionTypeService,
    PosessionTypePopupService,
    PosessionTypeComponent,
    PosessionTypeDetailComponent,
    PosessionTypeDialogComponent,
    PosessionTypePopupComponent,
    PosessionTypeDeletePopupComponent,
    PosessionTypeDeleteDialogComponent,
    posessionTypeRoute,
    posessionTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...posessionTypeRoute,
    ...posessionTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PosessionTypeComponent,
        PosessionTypeDetailComponent,
        PosessionTypeDialogComponent,
        PosessionTypeDeleteDialogComponent,
        PosessionTypePopupComponent,
        PosessionTypeDeletePopupComponent,
    ],
    entryComponents: [
        PosessionTypeComponent,
        PosessionTypeDialogComponent,
        PosessionTypePopupComponent,
        PosessionTypeDeleteDialogComponent,
        PosessionTypeDeletePopupComponent,
    ],
    providers: [
        PosessionTypeService,
        PosessionTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPosessionTypeModule {}
