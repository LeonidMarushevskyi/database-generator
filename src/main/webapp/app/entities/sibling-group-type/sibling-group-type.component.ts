import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { SiblingGroupType } from './sibling-group-type.model';
import { SiblingGroupTypeService } from './sibling-group-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-sibling-group-type',
    templateUrl: './sibling-group-type.component.html'
})
export class SiblingGroupTypeComponent implements OnInit, OnDestroy {
siblingGroupTypes: SiblingGroupType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private siblingGroupTypeService: SiblingGroupTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.siblingGroupTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.siblingGroupTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSiblingGroupTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SiblingGroupType) {
        return item.id;
    }
    registerChangeInSiblingGroupTypes() {
        this.eventSubscriber = this.eventManager.subscribe('siblingGroupTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
