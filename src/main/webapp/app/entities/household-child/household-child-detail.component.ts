import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { HouseholdChild } from './household-child.model';
import { HouseholdChildService } from './household-child.service';

@Component({
    selector: 'jhi-household-child-detail',
    templateUrl: './household-child-detail.component.html'
})
export class HouseholdChildDetailComponent implements OnInit, OnDestroy {

    householdChild: HouseholdChild;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private householdChildService: HouseholdChildService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHouseholdChildren();
    }

    load(id) {
        this.householdChildService.find(id).subscribe((householdChild) => {
            this.householdChild = householdChild;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHouseholdChildren() {
        this.eventSubscriber = this.eventManager.subscribe(
            'householdChildListModification',
            (response) => this.load(this.householdChild.id)
        );
    }
}
