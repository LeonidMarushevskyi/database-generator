import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { FacilityAddress } from './facility-address.model';
import { FacilityAddressService } from './facility-address.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-facility-address',
    templateUrl: './facility-address.component.html'
})
export class FacilityAddressComponent implements OnInit, OnDestroy {
facilityAddresses: FacilityAddress[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private facilityAddressService: FacilityAddressService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.facilityAddressService.query().subscribe(
            (res: Response) => {
                this.facilityAddresses = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFacilityAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: FacilityAddress) {
        return item.id;
    }



    registerChangeInFacilityAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('facilityAddressListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
