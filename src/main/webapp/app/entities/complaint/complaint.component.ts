import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Complaint } from './complaint.model';
import { ComplaintService } from './complaint.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-complaint',
    templateUrl: './complaint.component.html'
})
export class ComplaintComponent implements OnInit, OnDestroy {
complaints: Complaint[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private complaintService: ComplaintService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.complaintService.query().subscribe(
            (res: ResponseWrapper) => {
                this.complaints = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInComplaints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Complaint) {
        return item.id;
    }
    registerChangeInComplaints() {
        this.eventSubscriber = this.eventManager.subscribe('complaintListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
