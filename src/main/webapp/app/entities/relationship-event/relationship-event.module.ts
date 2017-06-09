import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    RelationshipEventService,
    RelationshipEventPopupService,
    RelationshipEventComponent,
    RelationshipEventDetailComponent,
    RelationshipEventDialogComponent,
    RelationshipEventPopupComponent,
    RelationshipEventDeletePopupComponent,
    RelationshipEventDeleteDialogComponent,
    relationshipEventRoute,
    relationshipEventPopupRoute,
} from './';

const ENTITY_STATES = [
    ...relationshipEventRoute,
    ...relationshipEventPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RelationshipEventComponent,
        RelationshipEventDetailComponent,
        RelationshipEventDialogComponent,
        RelationshipEventDeleteDialogComponent,
        RelationshipEventPopupComponent,
        RelationshipEventDeletePopupComponent,
    ],
    entryComponents: [
        RelationshipEventComponent,
        RelationshipEventDialogComponent,
        RelationshipEventPopupComponent,
        RelationshipEventDeleteDialogComponent,
        RelationshipEventDeletePopupComponent,
    ],
    providers: [
        RelationshipEventService,
        RelationshipEventPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorRelationshipEventModule {}
