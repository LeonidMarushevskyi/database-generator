import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { RaceType } from './race-type.model';
import { RaceTypeService } from './race-type.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-race-type',
    templateUrl: './race-type.component.html'
})
export class RaceTypeComponent implements OnInit, OnDestroy {
raceTypes: RaceType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private raceTypeService: RaceTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.raceTypeService.query().subscribe(
            (res: Response) => {
                this.raceTypes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRaceTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: RaceType) {
        return item.id;
    }



    registerChangeInRaceTypes() {
        this.eventSubscriber = this.eventManager.subscribe('raceTypeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
