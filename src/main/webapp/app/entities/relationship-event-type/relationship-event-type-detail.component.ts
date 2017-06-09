import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { RelationshipEventType } from './relationship-event-type.model';
import { RelationshipEventTypeService } from './relationship-event-type.service';

@Component({
    selector: 'jhi-relationship-event-type-detail',
    templateUrl: './relationship-event-type-detail.component.html'
})
export class RelationshipEventTypeDetailComponent implements OnInit, OnDestroy {

    relationshipEventType: RelationshipEventType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private relationshipEventTypeService: RelationshipEventTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRelationshipEventTypes();
    }

    load(id) {
        this.relationshipEventTypeService.find(id).subscribe((relationshipEventType) => {
            this.relationshipEventType = relationshipEventType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRelationshipEventTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'relationshipEventTypeListModification',
            (response) => this.load(this.relationshipEventType.id)
        );
    }
}
