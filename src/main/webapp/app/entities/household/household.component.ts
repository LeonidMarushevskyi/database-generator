import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Household } from './household.model';
import { HouseholdService } from './household.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-household',
    templateUrl: './household.component.html'
})
export class HouseholdComponent implements OnInit, OnDestroy {
households: Household[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private householdService: HouseholdService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.householdService.query().subscribe(
            (res: ResponseWrapper) => {
                this.households = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHouseholds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Household) {
        return item.id;
    }
    registerChangeInHouseholds() {
        this.eventSubscriber = this.eventManager.subscribe('householdListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
