import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FacilityAddress } from './facility-address.model';
import { FacilityAddressService } from './facility-address.service';

@Component({
    selector: 'jhi-facility-address-detail',
    templateUrl: './facility-address-detail.component.html'
})
export class FacilityAddressDetailComponent implements OnInit, OnDestroy {

    facilityAddress: FacilityAddress;
    private subscription: any;

    constructor(
        private facilityAddressService: FacilityAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.facilityAddressService.find(id).subscribe(facilityAddress => {
            this.facilityAddress = facilityAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
