import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { EducationPoint } from './education-point.model';
import { EducationPointService } from './education-point.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-education-point',
    templateUrl: './education-point.component.html'
})
export class EducationPointComponent implements OnInit, OnDestroy {
educationPoints: EducationPoint[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private educationPointService: EducationPointService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.educationPointService.query().subscribe(
            (res: ResponseWrapper) => {
                this.educationPoints = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEducationPoints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EducationPoint) {
        return item.id;
    }
    registerChangeInEducationPoints() {
        this.eventSubscriber = this.eventManager.subscribe('educationPointListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
