import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PersonAddress } from './person-address.model';
import { PersonAddressService } from './person-address.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-person-address',
    templateUrl: './person-address.component.html'
})
export class PersonAddressComponent implements OnInit, OnDestroy {
personAddresses: PersonAddress[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personAddressService: PersonAddressService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.personAddressService.query().subscribe(
            (res: Response) => {
                this.personAddresses = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: PersonAddress) {
        return item.id;
    }



    registerChangeInPersonAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('personAddressListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
