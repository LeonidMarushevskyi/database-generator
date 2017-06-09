import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService, DataUtils } from 'ng-jhipster';

import { CriminalRecord } from './criminal-record.model';
import { CriminalRecordService } from './criminal-record.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-criminal-record',
    templateUrl: './criminal-record.component.html'
})
export class CriminalRecordComponent implements OnInit, OnDestroy {
criminalRecords: CriminalRecord[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private criminalRecordService: CriminalRecordService,
        private alertService: AlertService,
        private dataUtils: DataUtils,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.criminalRecordService.query().subscribe(
            (res: ResponseWrapper) => {
                this.criminalRecords = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCriminalRecords();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CriminalRecord) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInCriminalRecords() {
        this.eventSubscriber = this.eventManager.subscribe('criminalRecordListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
