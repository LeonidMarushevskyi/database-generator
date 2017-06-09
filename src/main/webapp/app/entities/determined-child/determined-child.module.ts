import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    DeterminedChildService,
    DeterminedChildPopupService,
    DeterminedChildComponent,
    DeterminedChildDetailComponent,
    DeterminedChildDialogComponent,
    DeterminedChildPopupComponent,
    DeterminedChildDeletePopupComponent,
    DeterminedChildDeleteDialogComponent,
    determinedChildRoute,
    determinedChildPopupRoute,
} from './';

const ENTITY_STATES = [
    ...determinedChildRoute,
    ...determinedChildPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DeterminedChildComponent,
        DeterminedChildDetailComponent,
        DeterminedChildDialogComponent,
        DeterminedChildDeleteDialogComponent,
        DeterminedChildPopupComponent,
        DeterminedChildDeletePopupComponent,
    ],
    entryComponents: [
        DeterminedChildComponent,
        DeterminedChildDialogComponent,
        DeterminedChildPopupComponent,
        DeterminedChildDeleteDialogComponent,
        DeterminedChildDeletePopupComponent,
    ],
    providers: [
        DeterminedChildService,
        DeterminedChildPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorDeterminedChildModule {}
