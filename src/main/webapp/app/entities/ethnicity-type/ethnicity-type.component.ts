import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { EthnicityType } from './ethnicity-type.model';
import { EthnicityTypeService } from './ethnicity-type.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ethnicity-type',
    templateUrl: './ethnicity-type.component.html'
})
export class EthnicityTypeComponent implements OnInit, OnDestroy {
ethnicityTypes: EthnicityType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ethnicityTypeService: EthnicityTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ethnicityTypeService.query().subscribe(
            (res: Response) => {
                this.ethnicityTypes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEthnicityTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: EthnicityType) {
        return item.id;
    }



    registerChangeInEthnicityTypes() {
        this.eventSubscriber = this.eventManager.subscribe('ethnicityTypeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
