import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { RelationshipType } from './relationship-type.model';
import { RelationshipTypeService } from './relationship-type.service';

@Component({
    selector: 'jhi-relationship-type-detail',
    templateUrl: './relationship-type-detail.component.html'
})
export class RelationshipTypeDetailComponent implements OnInit, OnDestroy {

    relationshipType: RelationshipType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private relationshipTypeService: RelationshipTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRelationshipTypes();
    }

    load(id) {
        this.relationshipTypeService.find(id).subscribe((relationshipType) => {
            this.relationshipType = relationshipType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRelationshipTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'relationshipTypeListModification',
            (response) => this.load(this.relationshipType.id)
        );
    }
}
