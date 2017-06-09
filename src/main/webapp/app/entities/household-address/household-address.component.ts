import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { HouseholdAddress } from './household-address.model';
import { HouseholdAddressService } from './household-address.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-household-address',
    templateUrl: './household-address.component.html'
})
export class HouseholdAddressComponent implements OnInit, OnDestroy {
householdAddresses: HouseholdAddress[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private householdAddressService: HouseholdAddressService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.householdAddressService.query().subscribe(
            (res: ResponseWrapper) => {
                this.householdAddresses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHouseholdAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HouseholdAddress) {
        return item.id;
    }
    registerChangeInHouseholdAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('householdAddressListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
