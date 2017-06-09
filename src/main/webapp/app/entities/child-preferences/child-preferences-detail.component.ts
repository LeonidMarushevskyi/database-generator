import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ChildPreferences } from './child-preferences.model';
import { ChildPreferencesService } from './child-preferences.service';

@Component({
    selector: 'jhi-child-preferences-detail',
    templateUrl: './child-preferences-detail.component.html'
})
export class ChildPreferencesDetailComponent implements OnInit, OnDestroy {

    childPreferences: ChildPreferences;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private childPreferencesService: ChildPreferencesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInChildPreferences();
    }

    load(id) {
        this.childPreferencesService.find(id).subscribe((childPreferences) => {
            this.childPreferences = childPreferences;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChildPreferences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'childPreferencesListModification',
            (response) => this.load(this.childPreferences.id)
        );
    }
}
