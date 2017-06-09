import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { EmailAddress } from './email-address.model';
import { EmailAddressService } from './email-address.service';

@Component({
    selector: 'jhi-email-address-detail',
    templateUrl: './email-address-detail.component.html'
})
export class EmailAddressDetailComponent implements OnInit, OnDestroy {

    emailAddress: EmailAddress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private emailAddressService: EmailAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmailAddresses();
    }

    load(id) {
        this.emailAddressService.find(id).subscribe((emailAddress) => {
            this.emailAddress = emailAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmailAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'emailAddressListModification',
            (response) => this.load(this.emailAddress.id)
        );
    }
}
