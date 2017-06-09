import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PhoneNumberType } from './phone-number-type.model';
import { PhoneNumberTypeService } from './phone-number-type.service';

@Component({
    selector: 'jhi-phone-number-type-detail',
    templateUrl: './phone-number-type-detail.component.html'
})
export class PhoneNumberTypeDetailComponent implements OnInit, OnDestroy {

    phoneNumberType: PhoneNumberType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private phoneNumberTypeService: PhoneNumberTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPhoneNumberTypes();
    }

    load(id) {
        this.phoneNumberTypeService.find(id).subscribe((phoneNumberType) => {
            this.phoneNumberType = phoneNumberType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPhoneNumberTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'phoneNumberTypeListModification',
            (response) => this.load(this.phoneNumberType.id)
        );
    }
}
