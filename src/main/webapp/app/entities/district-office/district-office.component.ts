import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { DistrictOffice } from './district-office.model';
import { DistrictOfficeService } from './district-office.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-district-office',
    templateUrl: './district-office.component.html'
})
export class DistrictOfficeComponent implements OnInit, OnDestroy {
districtOffices: DistrictOffice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private districtOfficeService: DistrictOfficeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.districtOfficeService.query().subscribe(
            (res: Response) => {
                this.districtOffices = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDistrictOffices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: DistrictOffice) {
        return item.id;
    }



    registerChangeInDistrictOffices() {
        this.eventSubscriber = this.eventManager.subscribe('districtOfficeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
