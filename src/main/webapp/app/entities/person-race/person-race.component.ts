import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PersonRace } from './person-race.model';
import { PersonRaceService } from './person-race.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-person-race',
    templateUrl: './person-race.component.html'
})
export class PersonRaceComponent implements OnInit, OnDestroy {
personRaces: PersonRace[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personRaceService: PersonRaceService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.personRaceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.personRaces = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonRaces();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PersonRace) {
        return item.id;
    }
    registerChangeInPersonRaces() {
        this.eventSubscriber = this.eventManager.subscribe('personRaceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
