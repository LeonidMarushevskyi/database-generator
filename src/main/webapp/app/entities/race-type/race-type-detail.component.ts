import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RaceType } from './race-type.model';
import { RaceTypeService } from './race-type.service';

@Component({
    selector: 'jhi-race-type-detail',
    templateUrl: './race-type-detail.component.html'
})
export class RaceTypeDetailComponent implements OnInit, OnDestroy {

    raceType: RaceType;
    private subscription: any;

    constructor(
        private raceTypeService: RaceTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.raceTypeService.find(id).subscribe(raceType => {
            this.raceType = raceType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
