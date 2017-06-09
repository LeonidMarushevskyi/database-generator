import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    RelationshipEventTypeService,
    RelationshipEventTypePopupService,
    RelationshipEventTypeComponent,
    RelationshipEventTypeDetailComponent,
    RelationshipEventTypeDialogComponent,
    RelationshipEventTypePopupComponent,
    RelationshipEventTypeDeletePopupComponent,
    RelationshipEventTypeDeleteDialogComponent,
    relationshipEventTypeRoute,
    relationshipEventTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...relationshipEventTypeRoute,
    ...relationshipEventTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RelationshipEventTypeComponent,
        RelationshipEventTypeDetailComponent,
        RelationshipEventTypeDialogComponent,
        RelationshipEventTypeDeleteDialogComponent,
        RelationshipEventTypePopupComponent,
        RelationshipEventTypeDeletePopupComponent,
    ],
    entryComponents: [
        RelationshipEventTypeComponent,
        RelationshipEventTypeDialogComponent,
        RelationshipEventTypePopupComponent,
        RelationshipEventTypeDeleteDialogComponent,
        RelationshipEventTypeDeletePopupComponent,
    ],
    providers: [
        RelationshipEventTypeService,
        RelationshipEventTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorRelationshipEventTypeModule {}
