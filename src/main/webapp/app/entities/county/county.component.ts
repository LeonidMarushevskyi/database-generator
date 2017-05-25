import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { County } from './county.model';
import { CountyService } from './county.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-county',
    templateUrl: './county.component.html'
})
export class CountyComponent implements OnInit, OnDestroy {
counties: County[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private countyService: CountyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.countyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.counties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCounties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: County) {
        return item.id;
    }
    registerChangeInCounties() {
        this.eventSubscriber = this.eventManager.subscribe('countyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
