import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Employment } from './employment.model';
import { EmploymentService } from './employment.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-employment',
    templateUrl: './employment.component.html'
})
export class EmploymentComponent implements OnInit, OnDestroy {
employments: Employment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employmentService: EmploymentService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employmentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employments = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Employment) {
        return item.id;
    }
    registerChangeInEmployments() {
        this.eventSubscriber = this.eventManager.subscribe('employmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
