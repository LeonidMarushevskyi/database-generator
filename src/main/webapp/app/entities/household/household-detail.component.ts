import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Household } from './household.model';
import { HouseholdService } from './household.service';

@Component({
    selector: 'jhi-household-detail',
    templateUrl: './household-detail.component.html'
})
export class HouseholdDetailComponent implements OnInit, OnDestroy {

    household: Household;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private householdService: HouseholdService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHouseholds();
    }

    load(id) {
        this.householdService.find(id).subscribe((household) => {
            this.household = household;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHouseholds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'householdListModification',
            (response) => this.load(this.household.id)
        );
    }
}
