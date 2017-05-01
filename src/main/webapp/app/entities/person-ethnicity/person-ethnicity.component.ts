import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PersonEthnicity } from './person-ethnicity.model';
import { PersonEthnicityService } from './person-ethnicity.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-person-ethnicity',
    templateUrl: './person-ethnicity.component.html'
})
export class PersonEthnicityComponent implements OnInit, OnDestroy {
personEthnicities: PersonEthnicity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personEthnicityService: PersonEthnicityService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.personEthnicityService.query().subscribe(
            (res: Response) => {
                this.personEthnicities = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonEthnicities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: PersonEthnicity) {
        return item.id;
    }



    registerChangeInPersonEthnicities() {
        this.eventSubscriber = this.eventManager.subscribe('personEthnicityListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
