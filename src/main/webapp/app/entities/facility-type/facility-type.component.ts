import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { FacilityType } from './facility-type.model';
import { FacilityTypeService } from './facility-type.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-facility-type',
    templateUrl: './facility-type.component.html'
})
export class FacilityTypeComponent implements OnInit, OnDestroy {
facilityTypes: FacilityType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private facilityTypeService: FacilityTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.facilityTypeService.query().subscribe(
            (res: Response) => {
                this.facilityTypes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFacilityTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: FacilityType) {
        return item.id;
    }



    registerChangeInFacilityTypes() {
        this.eventSubscriber = this.eventManager.subscribe('facilityTypeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
