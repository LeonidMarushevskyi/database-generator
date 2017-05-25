import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PersonAddress } from './person-address.model';
import { PersonAddressService } from './person-address.service';

@Component({
    selector: 'jhi-person-address-detail',
    templateUrl: './person-address-detail.component.html'
})
export class PersonAddressDetailComponent implements OnInit, OnDestroy {

    personAddress: PersonAddress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private personAddressService: PersonAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonAddresses();
    }

    load(id) {
        this.personAddressService.find(id).subscribe((personAddress) => {
            this.personAddress = personAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personAddressListModification',
            (response) => this.load(this.personAddress.id)
        );
    }
}
