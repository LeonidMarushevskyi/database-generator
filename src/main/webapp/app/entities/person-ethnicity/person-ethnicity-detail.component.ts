import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PersonEthnicity } from './person-ethnicity.model';
import { PersonEthnicityService } from './person-ethnicity.service';

@Component({
    selector: 'jhi-person-ethnicity-detail',
    templateUrl: './person-ethnicity-detail.component.html'
})
export class PersonEthnicityDetailComponent implements OnInit, OnDestroy {

    personEthnicity: PersonEthnicity;
    private subscription: any;

    constructor(
        private personEthnicityService: PersonEthnicityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.personEthnicityService.find(id).subscribe(personEthnicity => {
            this.personEthnicity = personEthnicity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
