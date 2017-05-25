import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { ClearedPOC } from './cleared-poc.model';
import { ClearedPOCService } from './cleared-poc.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-cleared-poc',
    templateUrl: './cleared-poc.component.html'
})
export class ClearedPOCComponent implements OnInit, OnDestroy {
clearedPOCS: ClearedPOC[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clearedPOCService: ClearedPOCService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clearedPOCService.query().subscribe(
            (res: ResponseWrapper) => {
                this.clearedPOCS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClearedPOCS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClearedPOC) {
        return item.id;
    }
    registerChangeInClearedPOCS() {
        this.eventSubscriber = this.eventManager.subscribe('clearedPOCListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
