import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { RaceType } from './race-type.model';
import { RaceTypeService } from './race-type.service';

@Component({
    selector: 'jhi-race-type-detail',
    templateUrl: './race-type-detail.component.html'
})
export class RaceTypeDetailComponent implements OnInit, OnDestroy {

    raceType: RaceType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private raceTypeService: RaceTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRaceTypes();
    }

    load(id) {
        this.raceTypeService.find(id).subscribe((raceType) => {
            this.raceType = raceType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRaceTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'raceTypeListModification',
            (response) => this.load(this.raceType.id)
        );
    }
}
