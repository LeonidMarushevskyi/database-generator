import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Relationship } from './relationship.model';
import { RelationshipService } from './relationship.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-relationship',
    templateUrl: './relationship.component.html'
})
export class RelationshipComponent implements OnInit, OnDestroy {
relationships: Relationship[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private relationshipService: RelationshipService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.relationshipService.query().subscribe(
            (res: ResponseWrapper) => {
                this.relationships = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRelationships();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Relationship) {
        return item.id;
    }
    registerChangeInRelationships() {
        this.eventSubscriber = this.eventManager.subscribe('relationshipListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
