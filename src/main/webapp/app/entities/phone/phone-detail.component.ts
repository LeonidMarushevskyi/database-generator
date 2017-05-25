import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Phone } from './phone.model';
import { PhoneService } from './phone.service';

@Component({
    selector: 'jhi-phone-detail',
    templateUrl: './phone-detail.component.html'
})
export class PhoneDetailComponent implements OnInit, OnDestroy {

    phone: Phone;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private phoneService: PhoneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPhones();
    }

    load(id) {
        this.phoneService.find(id).subscribe((phone) => {
            this.phone = phone;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPhones() {
        this.eventSubscriber = this.eventManager.subscribe(
            'phoneListModification',
            (response) => this.load(this.phone.id)
        );
    }
}
