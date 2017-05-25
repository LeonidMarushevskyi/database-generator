import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PersonPhone } from './person-phone.model';
import { PersonPhoneService } from './person-phone.service';

@Component({
    selector: 'jhi-person-phone-detail',
    templateUrl: './person-phone-detail.component.html'
})
export class PersonPhoneDetailComponent implements OnInit, OnDestroy {

    personPhone: PersonPhone;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private personPhoneService: PersonPhoneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonPhones();
    }

    load(id) {
        this.personPhoneService.find(id).subscribe((personPhone) => {
            this.personPhone = personPhone;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonPhones() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personPhoneListModification',
            (response) => this.load(this.personPhone.id)
        );
    }
}
