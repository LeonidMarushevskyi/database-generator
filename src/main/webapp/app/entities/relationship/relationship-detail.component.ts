import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Relationship } from './relationship.model';
import { RelationshipService } from './relationship.service';

@Component({
    selector: 'jhi-relationship-detail',
    templateUrl: './relationship-detail.component.html'
})
export class RelationshipDetailComponent implements OnInit, OnDestroy {

    relationship: Relationship;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private relationshipService: RelationshipService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRelationships();
    }

    load(id) {
        this.relationshipService.find(id).subscribe((relationship) => {
            this.relationship = relationship;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRelationships() {
        this.eventSubscriber = this.eventManager.subscribe(
            'relationshipListModification',
            (response) => this.load(this.relationship.id)
        );
    }
}
