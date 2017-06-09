import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { RelationshipEventType } from './relationship-event-type.model';
import { RelationshipEventTypeService } from './relationship-event-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-relationship-event-type',
    templateUrl: './relationship-event-type.component.html'
})
export class RelationshipEventTypeComponent implements OnInit, OnDestroy {
relationshipEventTypes: RelationshipEventType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private relationshipEventTypeService: RelationshipEventTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.relationshipEventTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.relationshipEventTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRelationshipEventTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RelationshipEventType) {
        return item.id;
    }
    registerChangeInRelationshipEventTypes() {
        this.eventSubscriber = this.eventManager.subscribe('relationshipEventTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
