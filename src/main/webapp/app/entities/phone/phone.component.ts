import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Phone } from './phone.model';
import { PhoneService } from './phone.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-phone',
    templateUrl: './phone.component.html'
})
export class PhoneComponent implements OnInit, OnDestroy {
phones: Phone[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private phoneService: PhoneService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.phoneService.query().subscribe(
            (res: ResponseWrapper) => {
                this.phones = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPhones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Phone) {
        return item.id;
    }
    registerChangeInPhones() {
        this.eventSubscriber = this.eventManager.subscribe('phoneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
