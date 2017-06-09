import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { HouseholdAddress } from './household-address.model';
import { HouseholdAddressService } from './household-address.service';

@Component({
    selector: 'jhi-household-address-detail',
    templateUrl: './household-address-detail.component.html'
})
export class HouseholdAddressDetailComponent implements OnInit, OnDestroy {

    householdAddress: HouseholdAddress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private householdAddressService: HouseholdAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHouseholdAddresses();
    }

    load(id) {
        this.householdAddressService.find(id).subscribe((householdAddress) => {
            this.householdAddress = householdAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHouseholdAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'householdAddressListModification',
            (response) => this.load(this.householdAddress.id)
        );
    }
}
