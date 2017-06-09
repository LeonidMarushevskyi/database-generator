import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    RelationshipService,
    RelationshipPopupService,
    RelationshipComponent,
    RelationshipDetailComponent,
    RelationshipDialogComponent,
    RelationshipPopupComponent,
    RelationshipDeletePopupComponent,
    RelationshipDeleteDialogComponent,
    relationshipRoute,
    relationshipPopupRoute,
} from './';

const ENTITY_STATES = [
    ...relationshipRoute,
    ...relationshipPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RelationshipComponent,
        RelationshipDetailComponent,
        RelationshipDialogComponent,
        RelationshipDeleteDialogComponent,
        RelationshipPopupComponent,
        RelationshipDeletePopupComponent,
    ],
    entryComponents: [
        RelationshipComponent,
        RelationshipDialogComponent,
        RelationshipPopupComponent,
        RelationshipDeleteDialogComponent,
        RelationshipDeletePopupComponent,
    ],
    providers: [
        RelationshipService,
        RelationshipPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorRelationshipModule {}
