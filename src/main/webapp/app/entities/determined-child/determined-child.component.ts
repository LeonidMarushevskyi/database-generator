import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { DeterminedChild } from './determined-child.model';
import { DeterminedChildService } from './determined-child.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-determined-child',
    templateUrl: './determined-child.component.html'
})
export class DeterminedChildComponent implements OnInit, OnDestroy {
determinedChildren: DeterminedChild[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private determinedChildService: DeterminedChildService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.determinedChildService.query().subscribe(
            (res: ResponseWrapper) => {
                this.determinedChildren = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDeterminedChildren();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DeterminedChild) {
        return item.id;
    }
    registerChangeInDeterminedChildren() {
        this.eventSubscriber = this.eventManager.subscribe('determinedChildListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
