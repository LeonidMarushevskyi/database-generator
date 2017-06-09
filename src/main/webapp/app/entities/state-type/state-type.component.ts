import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { StateType } from './state-type.model';
import { StateTypeService } from './state-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-state-type',
    templateUrl: './state-type.component.html'
})
export class StateTypeComponent implements OnInit, OnDestroy {
stateTypes: StateType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private stateTypeService: StateTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.stateTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.stateTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInStateTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: StateType) {
        return item.id;
    }
    registerChangeInStateTypes() {
        this.eventSubscriber = this.eventManager.subscribe('stateTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
