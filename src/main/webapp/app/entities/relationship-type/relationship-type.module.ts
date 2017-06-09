import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    RelationshipTypeService,
    RelationshipTypePopupService,
    RelationshipTypeComponent,
    RelationshipTypeDetailComponent,
    RelationshipTypeDialogComponent,
    RelationshipTypePopupComponent,
    RelationshipTypeDeletePopupComponent,
    RelationshipTypeDeleteDialogComponent,
    relationshipTypeRoute,
    relationshipTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...relationshipTypeRoute,
    ...relationshipTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RelationshipTypeComponent,
        RelationshipTypeDetailComponent,
        RelationshipTypeDialogComponent,
        RelationshipTypeDeleteDialogComponent,
        RelationshipTypePopupComponent,
        RelationshipTypeDeletePopupComponent,
    ],
    entryComponents: [
        RelationshipTypeComponent,
        RelationshipTypeDialogComponent,
        RelationshipTypePopupComponent,
        RelationshipTypeDeleteDialogComponent,
        RelationshipTypeDeletePopupComponent,
    ],
    providers: [
        RelationshipTypeService,
        RelationshipTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorRelationshipTypeModule {}
