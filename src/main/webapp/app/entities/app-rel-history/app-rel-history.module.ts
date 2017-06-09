import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    AppRelHistoryService,
    AppRelHistoryPopupService,
    AppRelHistoryComponent,
    AppRelHistoryDetailComponent,
    AppRelHistoryDialogComponent,
    AppRelHistoryPopupComponent,
    AppRelHistoryDeletePopupComponent,
    AppRelHistoryDeleteDialogComponent,
    appRelHistoryRoute,
    appRelHistoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...appRelHistoryRoute,
    ...appRelHistoryPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AppRelHistoryComponent,
        AppRelHistoryDetailComponent,
        AppRelHistoryDialogComponent,
        AppRelHistoryDeleteDialogComponent,
        AppRelHistoryPopupComponent,
        AppRelHistoryDeletePopupComponent,
    ],
    entryComponents: [
        AppRelHistoryComponent,
        AppRelHistoryDialogComponent,
        AppRelHistoryPopupComponent,
        AppRelHistoryDeleteDialogComponent,
        AppRelHistoryDeletePopupComponent,
    ],
    providers: [
        AppRelHistoryService,
        AppRelHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorAppRelHistoryModule {}
