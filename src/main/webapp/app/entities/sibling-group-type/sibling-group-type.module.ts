import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    SiblingGroupTypeService,
    SiblingGroupTypePopupService,
    SiblingGroupTypeComponent,
    SiblingGroupTypeDetailComponent,
    SiblingGroupTypeDialogComponent,
    SiblingGroupTypePopupComponent,
    SiblingGroupTypeDeletePopupComponent,
    SiblingGroupTypeDeleteDialogComponent,
    siblingGroupTypeRoute,
    siblingGroupTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...siblingGroupTypeRoute,
    ...siblingGroupTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SiblingGroupTypeComponent,
        SiblingGroupTypeDetailComponent,
        SiblingGroupTypeDialogComponent,
        SiblingGroupTypeDeleteDialogComponent,
        SiblingGroupTypePopupComponent,
        SiblingGroupTypeDeletePopupComponent,
    ],
    entryComponents: [
        SiblingGroupTypeComponent,
        SiblingGroupTypeDialogComponent,
        SiblingGroupTypePopupComponent,
        SiblingGroupTypeDeleteDialogComponent,
        SiblingGroupTypeDeletePopupComponent,
    ],
    providers: [
        SiblingGroupTypeService,
        SiblingGroupTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorSiblingGroupTypeModule {}
