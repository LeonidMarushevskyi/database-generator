import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { AddressType } from './address-type.model';
import { AddressTypeService } from './address-type.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-address-type',
    templateUrl: './address-type.component.html'
})
export class AddressTypeComponent implements OnInit, OnDestroy {
addressTypes: AddressType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private addressTypeService: AddressTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.addressTypeService.query().subscribe(
            (res: Response) => {
                this.addressTypes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAddressTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: AddressType) {
        return item.id;
    }



    registerChangeInAddressTypes() {
        this.eventSubscriber = this.eventManager.subscribe('addressTypeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
