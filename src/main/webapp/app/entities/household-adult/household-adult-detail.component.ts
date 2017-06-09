import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { HouseholdAdult } from './household-adult.model';
import { HouseholdAdultService } from './household-adult.service';

@Component({
    selector: 'jhi-household-adult-detail',
    templateUrl: './household-adult-detail.component.html'
})
export class HouseholdAdultDetailComponent implements OnInit, OnDestroy {

    householdAdult: HouseholdAdult;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private householdAdultService: HouseholdAdultService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHouseholdAdults();
    }

    load(id) {
        this.householdAdultService.find(id).subscribe((householdAdult) => {
            this.householdAdult = householdAdult;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHouseholdAdults() {
        this.eventSubscriber = this.eventManager.subscribe(
            'householdAdultListModification',
            (response) => this.load(this.householdAdult.id)
        );
    }
}
