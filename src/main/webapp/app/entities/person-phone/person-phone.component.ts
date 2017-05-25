import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PersonPhone } from './person-phone.model';
import { PersonPhoneService } from './person-phone.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-person-phone',
    templateUrl: './person-phone.component.html'
})
export class PersonPhoneComponent implements OnInit, OnDestroy {
personPhones: PersonPhone[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personPhoneService: PersonPhoneService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.personPhoneService.query().subscribe(
            (res: ResponseWrapper) => {
                this.personPhones = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonPhones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PersonPhone) {
        return item.id;
    }
    registerChangeInPersonPhones() {
        this.eventSubscriber = this.eventManager.subscribe('personPhoneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
