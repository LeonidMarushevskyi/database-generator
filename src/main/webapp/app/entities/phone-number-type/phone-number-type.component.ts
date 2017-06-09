import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PhoneNumberType } from './phone-number-type.model';
import { PhoneNumberTypeService } from './phone-number-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-phone-number-type',
    templateUrl: './phone-number-type.component.html'
})
export class PhoneNumberTypeComponent implements OnInit, OnDestroy {
phoneNumberTypes: PhoneNumberType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private phoneNumberTypeService: PhoneNumberTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.phoneNumberTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.phoneNumberTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPhoneNumberTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PhoneNumberType) {
        return item.id;
    }
    registerChangeInPhoneNumberTypes() {
        this.eventSubscriber = this.eventManager.subscribe('phoneNumberTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
