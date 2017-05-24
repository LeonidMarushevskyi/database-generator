import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PersonPhone } from './person-phone.model';
import { PersonPhoneService } from './person-phone.service';

@Component({
    selector: 'jhi-person-phone-detail',
    templateUrl: './person-phone-detail.component.html'
})
export class PersonPhoneDetailComponent implements OnInit, OnDestroy {

    personPhone: PersonPhone;
    private subscription: any;

    constructor(
        private personPhoneService: PersonPhoneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.personPhoneService.find(id).subscribe(personPhone => {
            this.personPhone = personPhone;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
