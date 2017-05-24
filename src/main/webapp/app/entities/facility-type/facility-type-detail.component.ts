import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FacilityType } from './facility-type.model';
import { FacilityTypeService } from './facility-type.service';

@Component({
    selector: 'jhi-facility-type-detail',
    templateUrl: './facility-type-detail.component.html'
})
export class FacilityTypeDetailComponent implements OnInit, OnDestroy {

    facilityType: FacilityType;
    private subscription: any;

    constructor(
        private facilityTypeService: FacilityTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.facilityTypeService.find(id).subscribe(facilityType => {
            this.facilityType = facilityType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
