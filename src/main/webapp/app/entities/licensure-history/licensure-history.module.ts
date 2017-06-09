import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    LicensureHistoryService,
    LicensureHistoryPopupService,
    LicensureHistoryComponent,
    LicensureHistoryDetailComponent,
    LicensureHistoryDialogComponent,
    LicensureHistoryPopupComponent,
    LicensureHistoryDeletePopupComponent,
    LicensureHistoryDeleteDialogComponent,
    licensureHistoryRoute,
    licensureHistoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...licensureHistoryRoute,
    ...licensureHistoryPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LicensureHistoryComponent,
        LicensureHistoryDetailComponent,
        LicensureHistoryDialogComponent,
        LicensureHistoryDeleteDialogComponent,
        LicensureHistoryPopupComponent,
        LicensureHistoryDeletePopupComponent,
    ],
    entryComponents: [
        LicensureHistoryComponent,
        LicensureHistoryDialogComponent,
        LicensureHistoryPopupComponent,
        LicensureHistoryDeleteDialogComponent,
        LicensureHistoryDeletePopupComponent,
    ],
    providers: [
        LicensureHistoryService,
        LicensureHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorLicensureHistoryModule {}
