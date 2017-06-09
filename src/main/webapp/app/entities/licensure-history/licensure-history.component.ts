import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { LicensureHistory } from './licensure-history.model';
import { LicensureHistoryService } from './licensure-history.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-licensure-history',
    templateUrl: './licensure-history.component.html'
})
export class LicensureHistoryComponent implements OnInit, OnDestroy {
licensureHistories: LicensureHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private licensureHistoryService: LicensureHistoryService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.licensureHistoryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.licensureHistories = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLicensureHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LicensureHistory) {
        return item.id;
    }
    registerChangeInLicensureHistories() {
        this.eventSubscriber = this.eventManager.subscribe('licensureHistoryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
