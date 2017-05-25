import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { FacilityPhone } from './facility-phone.model';
import { FacilityPhoneService } from './facility-phone.service';

@Component({
    selector: 'jhi-facility-phone-detail',
    templateUrl: './facility-phone-detail.component.html'
})
export class FacilityPhoneDetailComponent implements OnInit, OnDestroy {

    facilityPhone: FacilityPhone;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private facilityPhoneService: FacilityPhoneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacilityPhones();
    }

    load(id) {
        this.facilityPhoneService.find(id).subscribe((facilityPhone) => {
            this.facilityPhone = facilityPhone;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacilityPhones() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facilityPhoneListModification',
            (response) => this.load(this.facilityPhone.id)
        );
    }
}
