import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { HouseholdChild } from './household-child.model';
import { HouseholdChildService } from './household-child.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-household-child',
    templateUrl: './household-child.component.html'
})
export class HouseholdChildComponent implements OnInit, OnDestroy {
householdChildren: HouseholdChild[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private householdChildService: HouseholdChildService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.householdChildService.query().subscribe(
            (res: ResponseWrapper) => {
                this.householdChildren = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHouseholdChildren();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HouseholdChild) {
        return item.id;
    }
    registerChangeInHouseholdChildren() {
        this.eventSubscriber = this.eventManager.subscribe('householdChildListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
