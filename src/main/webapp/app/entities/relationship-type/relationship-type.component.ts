import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { RelationshipType } from './relationship-type.model';
import { RelationshipTypeService } from './relationship-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-relationship-type',
    templateUrl: './relationship-type.component.html'
})
export class RelationshipTypeComponent implements OnInit, OnDestroy {
relationshipTypes: RelationshipType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private relationshipTypeService: RelationshipTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.relationshipTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.relationshipTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRelationshipTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RelationshipType) {
        return item.id;
    }
    registerChangeInRelationshipTypes() {
        this.eventSubscriber = this.eventManager.subscribe('relationshipTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
