import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { CountyType } from './county-type.model';
import { CountyTypeService } from './county-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-county-type',
    templateUrl: './county-type.component.html'
})
export class CountyTypeComponent implements OnInit, OnDestroy {
countyTypes: CountyType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private countyTypeService: CountyTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.countyTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.countyTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCountyTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CountyType) {
        return item.id;
    }
    registerChangeInCountyTypes() {
        this.eventSubscriber = this.eventManager.subscribe('countyTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
