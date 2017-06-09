import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { RelationshipEvent } from './relationship-event.model';
import { RelationshipEventService } from './relationship-event.service';

@Component({
    selector: 'jhi-relationship-event-detail',
    templateUrl: './relationship-event-detail.component.html'
})
export class RelationshipEventDetailComponent implements OnInit, OnDestroy {

    relationshipEvent: RelationshipEvent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private relationshipEventService: RelationshipEventService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRelationshipEvents();
    }

    load(id) {
        this.relationshipEventService.find(id).subscribe((relationshipEvent) => {
            this.relationshipEvent = relationshipEvent;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRelationshipEvents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'relationshipEventListModification',
            (response) => this.load(this.relationshipEvent.id)
        );
    }
}
