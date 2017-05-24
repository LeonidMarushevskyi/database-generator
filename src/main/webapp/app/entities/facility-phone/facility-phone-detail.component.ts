import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FacilityPhone } from './facility-phone.model';
import { FacilityPhoneService } from './facility-phone.service';

@Component({
    selector: 'jhi-facility-phone-detail',
    templateUrl: './facility-phone-detail.component.html'
})
export class FacilityPhoneDetailComponent implements OnInit, OnDestroy {

    facilityPhone: FacilityPhone;
    private subscription: any;

    constructor(
        private facilityPhoneService: FacilityPhoneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.facilityPhoneService.find(id).subscribe(facilityPhone => {
            this.facilityPhone = facilityPhone;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
