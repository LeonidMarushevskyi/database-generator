import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PersonRace } from './person-race.model';
import { PersonRaceService } from './person-race.service';

@Component({
    selector: 'jhi-person-race-detail',
    templateUrl: './person-race-detail.component.html'
})
export class PersonRaceDetailComponent implements OnInit, OnDestroy {

    personRace: PersonRace;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private personRaceService: PersonRaceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonRaces();
    }

    load(id) {
        this.personRaceService.find(id).subscribe((personRace) => {
            this.personRace = personRace;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonRaces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personRaceListModification',
            (response) => this.load(this.personRace.id)
        );
    }
}
