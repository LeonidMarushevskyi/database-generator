import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    HouseholdService,
    HouseholdPopupService,
    HouseholdComponent,
    HouseholdDetailComponent,
    HouseholdDialogComponent,
    HouseholdPopupComponent,
    HouseholdDeletePopupComponent,
    HouseholdDeleteDialogComponent,
    householdRoute,
    householdPopupRoute,
} from './';

const ENTITY_STATES = [
    ...householdRoute,
    ...householdPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HouseholdComponent,
        HouseholdDetailComponent,
        HouseholdDialogComponent,
        HouseholdDeleteDialogComponent,
        HouseholdPopupComponent,
        HouseholdDeletePopupComponent,
    ],
    entryComponents: [
        HouseholdComponent,
        HouseholdDialogComponent,
        HouseholdPopupComponent,
        HouseholdDeleteDialogComponent,
        HouseholdDeletePopupComponent,
    ],
    providers: [
        HouseholdService,
        HouseholdPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorHouseholdModule {}
