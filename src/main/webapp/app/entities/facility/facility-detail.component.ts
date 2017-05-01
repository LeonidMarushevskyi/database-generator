import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Facility } from './facility.model';
import { FacilityService } from './facility.service';

@Component({
    selector: 'jhi-facility-detail',
    templateUrl: './facility-detail.component.html'
})
export class FacilityDetailComponent implements OnInit, OnDestroy {

    facility: Facility;
    private subscription: any;

    constructor(
        private facilityService: FacilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.facilityService.find(id).subscribe(facility => {
            this.facility = facility;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
