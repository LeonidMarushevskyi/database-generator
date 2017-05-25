import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Inspection } from './inspection.model';
import { InspectionService } from './inspection.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-inspection',
    templateUrl: './inspection.component.html'
})
export class InspectionComponent implements OnInit, OnDestroy {
inspections: Inspection[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inspectionService: InspectionService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inspectionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.inspections = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInspections();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Inspection) {
        return item.id;
    }
    registerChangeInInspections() {
        this.eventSubscriber = this.eventManager.subscribe('inspectionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
