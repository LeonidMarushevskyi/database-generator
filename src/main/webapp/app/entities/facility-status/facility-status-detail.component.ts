import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FacilityStatus } from './facility-status.model';
import { FacilityStatusService } from './facility-status.service';

@Component({
    selector: 'jhi-facility-status-detail',
    templateUrl: './facility-status-detail.component.html'
})
export class FacilityStatusDetailComponent implements OnInit, OnDestroy {

    facilityStatus: FacilityStatus;
    private subscription: any;

    constructor(
        private facilityStatusService: FacilityStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.facilityStatusService.find(id).subscribe(facilityStatus => {
            this.facilityStatus = facilityStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
