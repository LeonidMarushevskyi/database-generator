import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { EmailAddress } from './email-address.model';
import { EmailAddressService } from './email-address.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-email-address',
    templateUrl: './email-address.component.html'
})
export class EmailAddressComponent implements OnInit, OnDestroy {
emailAddresses: EmailAddress[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private emailAddressService: EmailAddressService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.emailAddressService.query().subscribe(
            (res: ResponseWrapper) => {
                this.emailAddresses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmailAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmailAddress) {
        return item.id;
    }
    registerChangeInEmailAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('emailAddressListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
