import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { GenderType } from './gender-type.model';
import { GenderTypeService } from './gender-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-gender-type',
    templateUrl: './gender-type.component.html'
})
export class GenderTypeComponent implements OnInit, OnDestroy {
genderTypes: GenderType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private genderTypeService: GenderTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.genderTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.genderTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGenderTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GenderType) {
        return item.id;
    }
    registerChangeInGenderTypes() {
        this.eventSubscriber = this.eventManager.subscribe('genderTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
