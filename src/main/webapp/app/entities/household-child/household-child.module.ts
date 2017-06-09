import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    HouseholdChildService,
    HouseholdChildPopupService,
    HouseholdChildComponent,
    HouseholdChildDetailComponent,
    HouseholdChildDialogComponent,
    HouseholdChildPopupComponent,
    HouseholdChildDeletePopupComponent,
    HouseholdChildDeleteDialogComponent,
    householdChildRoute,
    householdChildPopupRoute,
} from './';

const ENTITY_STATES = [
    ...householdChildRoute,
    ...householdChildPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HouseholdChildComponent,
        HouseholdChildDetailComponent,
        HouseholdChildDialogComponent,
        HouseholdChildDeleteDialogComponent,
        HouseholdChildPopupComponent,
        HouseholdChildDeletePopupComponent,
    ],
    entryComponents: [
        HouseholdChildComponent,
        HouseholdChildDialogComponent,
        HouseholdChildPopupComponent,
        HouseholdChildDeleteDialogComponent,
        HouseholdChildDeletePopupComponent,
    ],
    providers: [
        HouseholdChildService,
        HouseholdChildPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorHouseholdChildModule {}
