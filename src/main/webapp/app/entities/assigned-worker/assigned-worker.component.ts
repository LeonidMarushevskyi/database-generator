import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { AssignedWorker } from './assigned-worker.model';
import { AssignedWorkerService } from './assigned-worker.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-assigned-worker',
    templateUrl: './assigned-worker.component.html'
})
export class AssignedWorkerComponent implements OnInit, OnDestroy {
assignedWorkers: AssignedWorker[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private assignedWorkerService: AssignedWorkerService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.assignedWorkerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.assignedWorkers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAssignedWorkers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AssignedWorker) {
        return item.id;
    }
    registerChangeInAssignedWorkers() {
        this.eventSubscriber = this.eventManager.subscribe('assignedWorkerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
