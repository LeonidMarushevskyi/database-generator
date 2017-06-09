import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    HouseholdAdultService,
    HouseholdAdultPopupService,
    HouseholdAdultComponent,
    HouseholdAdultDetailComponent,
    HouseholdAdultDialogComponent,
    HouseholdAdultPopupComponent,
    HouseholdAdultDeletePopupComponent,
    HouseholdAdultDeleteDialogComponent,
    householdAdultRoute,
    householdAdultPopupRoute,
} from './';

const ENTITY_STATES = [
    ...householdAdultRoute,
    ...householdAdultPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HouseholdAdultComponent,
        HouseholdAdultDetailComponent,
        HouseholdAdultDialogComponent,
        HouseholdAdultDeleteDialogComponent,
        HouseholdAdultPopupComponent,
        HouseholdAdultDeletePopupComponent,
    ],
    entryComponents: [
        HouseholdAdultComponent,
        HouseholdAdultDialogComponent,
        HouseholdAdultPopupComponent,
        HouseholdAdultDeleteDialogComponent,
        HouseholdAdultDeletePopupComponent,
    ],
    providers: [
        HouseholdAdultService,
        HouseholdAdultPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorHouseholdAdultModule {}
