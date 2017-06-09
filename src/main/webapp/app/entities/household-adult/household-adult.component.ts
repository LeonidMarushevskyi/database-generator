import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { HouseholdAdult } from './household-adult.model';
import { HouseholdAdultService } from './household-adult.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-household-adult',
    templateUrl: './household-adult.component.html'
})
export class HouseholdAdultComponent implements OnInit, OnDestroy {
householdAdults: HouseholdAdult[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private householdAdultService: HouseholdAdultService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.householdAdultService.query().subscribe(
            (res: ResponseWrapper) => {
                this.householdAdults = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHouseholdAdults();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HouseholdAdult) {
        return item.id;
    }
    registerChangeInHouseholdAdults() {
        this.eventSubscriber = this.eventManager.subscribe('householdAdultListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
