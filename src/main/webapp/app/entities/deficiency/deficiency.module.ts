import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    DeficiencyService,
    DeficiencyPopupService,
    DeficiencyComponent,
    DeficiencyDetailComponent,
    DeficiencyDialogComponent,
    DeficiencyPopupComponent,
    DeficiencyDeletePopupComponent,
    DeficiencyDeleteDialogComponent,
    deficiencyRoute,
    deficiencyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...deficiencyRoute,
    ...deficiencyPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DeficiencyComponent,
        DeficiencyDetailComponent,
        DeficiencyDialogComponent,
        DeficiencyDeleteDialogComponent,
        DeficiencyPopupComponent,
        DeficiencyDeletePopupComponent,
    ],
    entryComponents: [
        DeficiencyComponent,
        DeficiencyDialogComponent,
        DeficiencyPopupComponent,
        DeficiencyDeleteDialogComponent,
        DeficiencyDeletePopupComponent,
    ],
    providers: [
        DeficiencyService,
        DeficiencyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorDeficiencyModule {}
