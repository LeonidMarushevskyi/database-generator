import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { FacilityAddress } from './facility-address.model';
import { FacilityAddressService } from './facility-address.service';

@Component({
    selector: 'jhi-facility-address-detail',
    templateUrl: './facility-address-detail.component.html'
})
export class FacilityAddressDetailComponent implements OnInit, OnDestroy {

    facilityAddress: FacilityAddress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private facilityAddressService: FacilityAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacilityAddresses();
    }

    load(id) {
        this.facilityAddressService.find(id).subscribe((facilityAddress) => {
            this.facilityAddress = facilityAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacilityAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facilityAddressListModification',
            (response) => this.load(this.facilityAddress.id)
        );
    }
}
