import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { ChildPreferences } from './child-preferences.model';
import { ChildPreferencesService } from './child-preferences.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-child-preferences',
    templateUrl: './child-preferences.component.html'
})
export class ChildPreferencesComponent implements OnInit, OnDestroy {
childPreferences: ChildPreferences[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private childPreferencesService: ChildPreferencesService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.childPreferencesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.childPreferences = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInChildPreferences();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ChildPreferences) {
        return item.id;
    }
    registerChangeInChildPreferences() {
        this.eventSubscriber = this.eventManager.subscribe('childPreferencesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
