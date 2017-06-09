import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { BodyOfWater } from './body-of-water.model';
import { BodyOfWaterService } from './body-of-water.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-body-of-water',
    templateUrl: './body-of-water.component.html'
})
export class BodyOfWaterComponent implements OnInit, OnDestroy {
bodyOfWaters: BodyOfWater[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bodyOfWaterService: BodyOfWaterService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bodyOfWaterService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bodyOfWaters = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBodyOfWaters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BodyOfWater) {
        return item.id;
    }
    registerChangeInBodyOfWaters() {
        this.eventSubscriber = this.eventManager.subscribe('bodyOfWaterListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
