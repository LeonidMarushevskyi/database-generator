import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PersonAddress } from './person-address.model';
import { PersonAddressService } from './person-address.service';

@Component({
    selector: 'jhi-person-address-detail',
    templateUrl: './person-address-detail.component.html'
})
export class PersonAddressDetailComponent implements OnInit, OnDestroy {

    personAddress: PersonAddress;
    private subscription: any;

    constructor(
        private personAddressService: PersonAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.personAddressService.find(id).subscribe(personAddress => {
            this.personAddress = personAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
