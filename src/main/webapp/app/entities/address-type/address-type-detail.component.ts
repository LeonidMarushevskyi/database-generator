import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AddressType } from './address-type.model';
import { AddressTypeService } from './address-type.service';

@Component({
    selector: 'jhi-address-type-detail',
    templateUrl: './address-type-detail.component.html'
})
export class AddressTypeDetailComponent implements OnInit, OnDestroy {

    addressType: AddressType;
    private subscription: any;

    constructor(
        private addressTypeService: AddressTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.addressTypeService.find(id).subscribe(addressType => {
            this.addressType = addressType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
