import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Deficiency } from './deficiency.model';
import { DeficiencyService } from './deficiency.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-deficiency',
    templateUrl: './deficiency.component.html'
})
export class DeficiencyComponent implements OnInit, OnDestroy {
deficiencies: Deficiency[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private deficiencyService: DeficiencyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.deficiencyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.deficiencies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDeficiencies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Deficiency) {
        return item.id;
    }
    registerChangeInDeficiencies() {
        this.eventSubscriber = this.eventManager.subscribe('deficiencyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
