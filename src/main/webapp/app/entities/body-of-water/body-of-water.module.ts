import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    BodyOfWaterService,
    BodyOfWaterPopupService,
    BodyOfWaterComponent,
    BodyOfWaterDetailComponent,
    BodyOfWaterDialogComponent,
    BodyOfWaterPopupComponent,
    BodyOfWaterDeletePopupComponent,
    BodyOfWaterDeleteDialogComponent,
    bodyOfWaterRoute,
    bodyOfWaterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bodyOfWaterRoute,
    ...bodyOfWaterPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BodyOfWaterComponent,
        BodyOfWaterDetailComponent,
        BodyOfWaterDialogComponent,
        BodyOfWaterDeleteDialogComponent,
        BodyOfWaterPopupComponent,
        BodyOfWaterDeletePopupComponent,
    ],
    entryComponents: [
        BodyOfWaterComponent,
        BodyOfWaterDialogComponent,
        BodyOfWaterPopupComponent,
        BodyOfWaterDeleteDialogComponent,
        BodyOfWaterDeletePopupComponent,
    ],
    providers: [
        BodyOfWaterService,
        BodyOfWaterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorBodyOfWaterModule {}
