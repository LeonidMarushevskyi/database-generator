import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { ApplicationStatusType } from './application-status-type.model';
import { ApplicationStatusTypeService } from './application-status-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-application-status-type',
    templateUrl: './application-status-type.component.html'
})
export class ApplicationStatusTypeComponent implements OnInit, OnDestroy {
applicationStatusTypes: ApplicationStatusType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private applicationStatusTypeService: ApplicationStatusTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.applicationStatusTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.applicationStatusTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInApplicationStatusTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ApplicationStatusType) {
        return item.id;
    }
    registerChangeInApplicationStatusTypes() {
        this.eventSubscriber = this.eventManager.subscribe('applicationStatusTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
