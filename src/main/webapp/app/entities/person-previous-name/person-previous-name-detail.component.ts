import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PersonPreviousName } from './person-previous-name.model';
import { PersonPreviousNameService } from './person-previous-name.service';

@Component({
    selector: 'jhi-person-previous-name-detail',
    templateUrl: './person-previous-name-detail.component.html'
})
export class PersonPreviousNameDetailComponent implements OnInit, OnDestroy {

    personPreviousName: PersonPreviousName;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private personPreviousNameService: PersonPreviousNameService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonPreviousNames();
    }

    load(id) {
        this.personPreviousNameService.find(id).subscribe((personPreviousName) => {
            this.personPreviousName = personPreviousName;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonPreviousNames() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personPreviousNameListModification',
            (response) => this.load(this.personPreviousName.id)
        );
    }
}
