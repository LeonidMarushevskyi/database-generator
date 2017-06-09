import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { AppRelHistory } from './app-rel-history.model';
import { AppRelHistoryService } from './app-rel-history.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-app-rel-history',
    templateUrl: './app-rel-history.component.html'
})
export class AppRelHistoryComponent implements OnInit, OnDestroy {
appRelHistories: AppRelHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private appRelHistoryService: AppRelHistoryService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.appRelHistoryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.appRelHistories = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAppRelHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AppRelHistory) {
        return item.id;
    }
    registerChangeInAppRelHistories() {
        this.eventSubscriber = this.eventManager.subscribe('appRelHistoryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
