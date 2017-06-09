import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { EducationLevelType } from './education-level-type.model';
import { EducationLevelTypeService } from './education-level-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-education-level-type',
    templateUrl: './education-level-type.component.html'
})
export class EducationLevelTypeComponent implements OnInit, OnDestroy {
educationLevelTypes: EducationLevelType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private educationLevelTypeService: EducationLevelTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.educationLevelTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.educationLevelTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEducationLevelTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EducationLevelType) {
        return item.id;
    }
    registerChangeInEducationLevelTypes() {
        this.eventSubscriber = this.eventManager.subscribe('educationLevelTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
