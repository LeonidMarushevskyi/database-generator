import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { FacilityStatus } from './facility-status.model';
import { FacilityStatusService } from './facility-status.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-facility-status',
    templateUrl: './facility-status.component.html'
})
export class FacilityStatusComponent implements OnInit, OnDestroy {
facilityStatuses: FacilityStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private facilityStatusService: FacilityStatusService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.facilityStatusService.query().subscribe(
            (res: Response) => {
                this.facilityStatuses = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFacilityStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: FacilityStatus) {
        return item.id;
    }



    registerChangeInFacilityStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('facilityStatusListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
