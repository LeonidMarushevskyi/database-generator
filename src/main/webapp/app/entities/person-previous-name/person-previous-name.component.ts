import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PersonPreviousName } from './person-previous-name.model';
import { PersonPreviousNameService } from './person-previous-name.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-person-previous-name',
    templateUrl: './person-previous-name.component.html'
})
export class PersonPreviousNameComponent implements OnInit, OnDestroy {
personPreviousNames: PersonPreviousName[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personPreviousNameService: PersonPreviousNameService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.personPreviousNameService.query().subscribe(
            (res: ResponseWrapper) => {
                this.personPreviousNames = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonPreviousNames();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PersonPreviousName) {
        return item.id;
    }
    registerChangeInPersonPreviousNames() {
        this.eventSubscriber = this.eventManager.subscribe('personPreviousNameListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
