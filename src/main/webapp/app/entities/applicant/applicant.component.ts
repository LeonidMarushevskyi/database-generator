import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Applicant } from './applicant.model';
import { ApplicantService } from './applicant.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-applicant',
    templateUrl: './applicant.component.html'
})
export class ApplicantComponent implements OnInit, OnDestroy {
applicants: Applicant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private applicantService: ApplicantService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.applicantService.query().subscribe(
            (res: ResponseWrapper) => {
                this.applicants = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInApplicants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Applicant) {
        return item.id;
    }
    registerChangeInApplicants() {
        this.eventSubscriber = this.eventManager.subscribe('applicantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
