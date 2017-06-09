import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { AgeGroupType } from './age-group-type.model';
import { AgeGroupTypeService } from './age-group-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-age-group-type',
    templateUrl: './age-group-type.component.html'
})
export class AgeGroupTypeComponent implements OnInit, OnDestroy {
ageGroupTypes: AgeGroupType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ageGroupTypeService: AgeGroupTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ageGroupTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ageGroupTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAgeGroupTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AgeGroupType) {
        return item.id;
    }
    registerChangeInAgeGroupTypes() {
        this.eventSubscriber = this.eventManager.subscribe('ageGroupTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
