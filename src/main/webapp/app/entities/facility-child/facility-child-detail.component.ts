import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FacilityChild } from './facility-child.model';
import { FacilityChildService } from './facility-child.service';

@Component({
    selector: 'jhi-facility-child-detail',
    templateUrl: './facility-child-detail.component.html'
})
export class FacilityChildDetailComponent implements OnInit, OnDestroy {

    facilityChild: FacilityChild;
    private subscription: any;

    constructor(
        private facilityChildService: FacilityChildService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.facilityChildService.find(id).subscribe(facilityChild => {
            this.facilityChild = facilityChild;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
