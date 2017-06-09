import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    PersonPreviousNameService,
    PersonPreviousNamePopupService,
    PersonPreviousNameComponent,
    PersonPreviousNameDetailComponent,
    PersonPreviousNameDialogComponent,
    PersonPreviousNamePopupComponent,
    PersonPreviousNameDeletePopupComponent,
    PersonPreviousNameDeleteDialogComponent,
    personPreviousNameRoute,
    personPreviousNamePopupRoute,
} from './';

const ENTITY_STATES = [
    ...personPreviousNameRoute,
    ...personPreviousNamePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonPreviousNameComponent,
        PersonPreviousNameDetailComponent,
        PersonPreviousNameDialogComponent,
        PersonPreviousNameDeleteDialogComponent,
        PersonPreviousNamePopupComponent,
        PersonPreviousNameDeletePopupComponent,
    ],
    entryComponents: [
        PersonPreviousNameComponent,
        PersonPreviousNameDialogComponent,
        PersonPreviousNamePopupComponent,
        PersonPreviousNameDeleteDialogComponent,
        PersonPreviousNameDeletePopupComponent,
    ],
    providers: [
        PersonPreviousNameService,
        PersonPreviousNamePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorPersonPreviousNameModule {}
