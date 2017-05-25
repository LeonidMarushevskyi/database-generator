import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PersonEthnicity } from './person-ethnicity.model';
import { PersonEthnicityService } from './person-ethnicity.service';

@Component({
    selector: 'jhi-person-ethnicity-detail',
    templateUrl: './person-ethnicity-detail.component.html'
})
export class PersonEthnicityDetailComponent implements OnInit, OnDestroy {

    personEthnicity: PersonEthnicity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private personEthnicityService: PersonEthnicityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonEthnicities();
    }

    load(id) {
        this.personEthnicityService.find(id).subscribe((personEthnicity) => {
            this.personEthnicity = personEthnicity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonEthnicities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personEthnicityListModification',
            (response) => this.load(this.personEthnicity.id)
        );
    }
}
