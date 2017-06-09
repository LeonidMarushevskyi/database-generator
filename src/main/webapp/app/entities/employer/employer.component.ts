import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Employer } from './employer.model';
import { EmployerService } from './employer.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-employer',
    templateUrl: './employer.component.html'
})
export class EmployerComponent implements OnInit, OnDestroy {
employers: Employer[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employerService: EmployerService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Employer) {
        return item.id;
    }
    registerChangeInEmployers() {
        this.eventSubscriber = this.eventManager.subscribe('employerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
