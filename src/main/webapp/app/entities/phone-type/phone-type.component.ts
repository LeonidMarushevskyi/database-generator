import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PhoneType } from './phone-type.model';
import { PhoneTypeService } from './phone-type.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-phone-type',
    templateUrl: './phone-type.component.html'
})
export class PhoneTypeComponent implements OnInit, OnDestroy {
phoneTypes: PhoneType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private phoneTypeService: PhoneTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.phoneTypeService.query().subscribe(
            (res: Response) => {
                this.phoneTypes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPhoneTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: PhoneType) {
        return item.id;
    }



    registerChangeInPhoneTypes() {
        this.eventSubscriber = this.eventManager.subscribe('phoneTypeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
