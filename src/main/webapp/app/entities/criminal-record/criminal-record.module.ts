import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    CriminalRecordService,
    CriminalRecordPopupService,
    CriminalRecordComponent,
    CriminalRecordDetailComponent,
    CriminalRecordDialogComponent,
    CriminalRecordPopupComponent,
    CriminalRecordDeletePopupComponent,
    CriminalRecordDeleteDialogComponent,
    criminalRecordRoute,
    criminalRecordPopupRoute,
} from './';

const ENTITY_STATES = [
    ...criminalRecordRoute,
    ...criminalRecordPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CriminalRecordComponent,
        CriminalRecordDetailComponent,
        CriminalRecordDialogComponent,
        CriminalRecordDeleteDialogComponent,
        CriminalRecordPopupComponent,
        CriminalRecordDeletePopupComponent,
    ],
    entryComponents: [
        CriminalRecordComponent,
        CriminalRecordDialogComponent,
        CriminalRecordPopupComponent,
        CriminalRecordDeleteDialogComponent,
        CriminalRecordDeletePopupComponent,
    ],
    providers: [
        CriminalRecordService,
        CriminalRecordPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorCriminalRecordModule {}
