import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PhoneNumber } from './phone-number.model';
import { PhoneNumberService } from './phone-number.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-phone-number',
    templateUrl: './phone-number.component.html'
})
export class PhoneNumberComponent implements OnInit, OnDestroy {
phoneNumbers: PhoneNumber[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private phoneNumberService: PhoneNumberService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.phoneNumberService.query().subscribe(
            (res: ResponseWrapper) => {
                this.phoneNumbers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPhoneNumbers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PhoneNumber) {
        return item.id;
    }
    registerChangeInPhoneNumbers() {
        this.eventSubscriber = this.eventManager.subscribe('phoneNumberListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
