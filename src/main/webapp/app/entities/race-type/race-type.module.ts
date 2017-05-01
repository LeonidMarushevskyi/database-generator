import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    RaceTypeService,
    RaceTypePopupService,
    RaceTypeComponent,
    RaceTypeDetailComponent,
    RaceTypeDialogComponent,
    RaceTypePopupComponent,
    RaceTypeDeletePopupComponent,
    RaceTypeDeleteDialogComponent,
    raceTypeRoute,
    raceTypePopupRoute,
} from './';

let ENTITY_STATES = [
    ...raceTypeRoute,
    ...raceTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RaceTypeComponent,
        RaceTypeDetailComponent,
        RaceTypeDialogComponent,
        RaceTypeDeleteDialogComponent,
        RaceTypePopupComponent,
        RaceTypeDeletePopupComponent,
    ],
    entryComponents: [
        RaceTypeComponent,
        RaceTypeDialogComponent,
        RaceTypePopupComponent,
        RaceTypeDeleteDialogComponent,
        RaceTypeDeletePopupComponent,
    ],
    providers: [
        RaceTypeService,
        RaceTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorRaceTypeModule {}
