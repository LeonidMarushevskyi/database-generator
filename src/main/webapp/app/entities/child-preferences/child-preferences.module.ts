import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    ChildPreferencesService,
    ChildPreferencesPopupService,
    ChildPreferencesComponent,
    ChildPreferencesDetailComponent,
    ChildPreferencesDialogComponent,
    ChildPreferencesPopupComponent,
    ChildPreferencesDeletePopupComponent,
    ChildPreferencesDeleteDialogComponent,
    childPreferencesRoute,
    childPreferencesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...childPreferencesRoute,
    ...childPreferencesPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ChildPreferencesComponent,
        ChildPreferencesDetailComponent,
        ChildPreferencesDialogComponent,
        ChildPreferencesDeleteDialogComponent,
        ChildPreferencesPopupComponent,
        ChildPreferencesDeletePopupComponent,
    ],
    entryComponents: [
        ChildPreferencesComponent,
        ChildPreferencesDialogComponent,
        ChildPreferencesPopupComponent,
        ChildPreferencesDeleteDialogComponent,
        ChildPreferencesDeletePopupComponent,
    ],
    providers: [
        ChildPreferencesService,
        ChildPreferencesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorChildPreferencesModule {}
