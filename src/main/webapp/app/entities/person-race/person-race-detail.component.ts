import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PersonRace } from './person-race.model';
import { PersonRaceService } from './person-race.service';

@Component({
    selector: 'jhi-person-race-detail',
    templateUrl: './person-race-detail.component.html'
})
export class PersonRaceDetailComponent implements OnInit, OnDestroy {

    personRace: PersonRace;
    private subscription: any;

    constructor(
        private personRaceService: PersonRaceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.personRaceService.find(id).subscribe(personRace => {
            this.personRace = personRace;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
