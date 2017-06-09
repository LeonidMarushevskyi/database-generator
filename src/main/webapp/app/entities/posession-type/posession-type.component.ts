import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PosessionType } from './posession-type.model';
import { PosessionTypeService } from './posession-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-posession-type',
    templateUrl: './posession-type.component.html'
})
export class PosessionTypeComponent implements OnInit, OnDestroy {
posessionTypes: PosessionType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private posessionTypeService: PosessionTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.posessionTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.posessionTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPosessionTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PosessionType) {
        return item.id;
    }
    registerChangeInPosessionTypes() {
        this.eventSubscriber = this.eventManager.subscribe('posessionTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
