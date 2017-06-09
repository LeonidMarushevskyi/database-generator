import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { RelationshipEvent } from './relationship-event.model';
import { RelationshipEventService } from './relationship-event.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-relationship-event',
    templateUrl: './relationship-event.component.html'
})
export class RelationshipEventComponent implements OnInit, OnDestroy {
relationshipEvents: RelationshipEvent[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private relationshipEventService: RelationshipEventService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.relationshipEventService.query().subscribe(
            (res: ResponseWrapper) => {
                this.relationshipEvents = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRelationshipEvents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RelationshipEvent) {
        return item.id;
    }
    registerChangeInRelationshipEvents() {
        this.eventSubscriber = this.eventManager.subscribe('relationshipEventListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
