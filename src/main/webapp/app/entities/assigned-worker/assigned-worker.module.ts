import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';

import {
    AssignedWorkerService,
    AssignedWorkerPopupService,
    AssignedWorkerComponent,
    AssignedWorkerDetailComponent,
    AssignedWorkerDialogComponent,
    AssignedWorkerPopupComponent,
    AssignedWorkerDeletePopupComponent,
    AssignedWorkerDeleteDialogComponent,
    assignedWorkerRoute,
    assignedWorkerPopupRoute,
} from './';

let ENTITY_STATES = [
    ...assignedWorkerRoute,
    ...assignedWorkerPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AssignedWorkerComponent,
        AssignedWorkerDetailComponent,
        AssignedWorkerDialogComponent,
        AssignedWorkerDeleteDialogComponent,
        AssignedWorkerPopupComponent,
        AssignedWorkerDeletePopupComponent,
    ],
    entryComponents: [
        AssignedWorkerComponent,
        AssignedWorkerDialogComponent,
        AssignedWorkerPopupComponent,
        AssignedWorkerDeleteDialogComponent,
        AssignedWorkerDeletePopupComponent,
    ],
    providers: [
        AssignedWorkerService,
        AssignedWorkerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorAssignedWorkerModule {}
