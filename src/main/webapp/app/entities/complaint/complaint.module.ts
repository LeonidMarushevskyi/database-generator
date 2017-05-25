import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    ComplaintService,
    ComplaintPopupService,
    ComplaintComponent,
    ComplaintDetailComponent,
    ComplaintDialogComponent,
    ComplaintPopupComponent,
    ComplaintDeletePopupComponent,
    ComplaintDeleteDialogComponent,
    complaintRoute,
    complaintPopupRoute,
} from './';

const ENTITY_STATES = [
    ...complaintRoute,
    ...complaintPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ComplaintComponent,
        ComplaintDetailComponent,
        ComplaintDialogComponent,
        ComplaintDeleteDialogComponent,
        ComplaintPopupComponent,
        ComplaintDeletePopupComponent,
    ],
    entryComponents: [
        ComplaintComponent,
        ComplaintDialogComponent,
        ComplaintPopupComponent,
        ComplaintDeleteDialogComponent,
        ComplaintDeletePopupComponent,
    ],
    providers: [
        ComplaintService,
        ComplaintPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorComplaintModule {}
