import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    PersonRaceService,
    PersonRacePopupService,
    PersonRaceComponent,
    PersonRaceDetailComponent,
    PersonRaceDialogComponent,
    PersonRacePopupComponent,
    PersonRaceDeletePopupComponent,
    PersonRaceDeleteDialogComponent,
    personRaceRoute,
    personRacePopupRoute,
} from './';

let ENTITY_STATES = [
    ...personRaceRoute,
    ...personRacePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonRaceComponent,
        PersonRaceDetailComponent,
        PersonRaceDialogComponent,
        PersonRaceDeleteDialogComponent,
        PersonRacePopupComponent,
        PersonRaceDeletePopupComponent,
    ],
    entryComponents: [
        PersonRaceComponent,
        PersonRaceDialogComponent,
        PersonRacePopupComponent,
        PersonRaceDeleteDialogComponent,
        PersonRaceDeletePopupComponent,
    ],
    providers: [
        PersonRaceService,
        PersonRacePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPersonRaceModule {}
