import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AddressType } from './address-type.model';
import { AddressTypeService } from './address-type.service';

@Component({
    selector: 'jhi-address-type-detail',
    templateUrl: './address-type-detail.component.html'
})
export class AddressTypeDetailComponent implements OnInit, OnDestroy {

    addressType: AddressType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private addressTypeService: AddressTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAddressTypes();
    }

    load(id) {
        this.addressTypeService.find(id).subscribe((addressType) => {
            this.addressType = addressType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAddressTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'addressTypeListModification',
            (response) => this.load(this.addressType.id)
        );
    }
}
