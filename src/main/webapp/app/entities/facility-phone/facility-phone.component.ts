import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { FacilityPhone } from './facility-phone.model';
import { FacilityPhoneService } from './facility-phone.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-facility-phone',
    templateUrl: './facility-phone.component.html'
})
export class FacilityPhoneComponent implements OnInit, OnDestroy {
facilityPhones: FacilityPhone[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private facilityPhoneService: FacilityPhoneService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.facilityPhoneService.query().subscribe(
            (res: ResponseWrapper) => {
                this.facilityPhones = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFacilityPhones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FacilityPhone) {
        return item.id;
    }
    registerChangeInFacilityPhones() {
        this.eventSubscriber = this.eventManager.subscribe('facilityPhoneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
