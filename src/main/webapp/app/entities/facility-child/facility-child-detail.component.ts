import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { FacilityChild } from './facility-child.model';
import { FacilityChildService } from './facility-child.service';

@Component({
    selector: 'jhi-facility-child-detail',
    templateUrl: './facility-child-detail.component.html'
})
export class FacilityChildDetailComponent implements OnInit, OnDestroy {

    facilityChild: FacilityChild;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private facilityChildService: FacilityChildService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacilityChildren();
    }

    load(id) {
        this.facilityChildService.find(id).subscribe((facilityChild) => {
            this.facilityChild = facilityChild;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacilityChildren() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facilityChildListModification',
            (response) => this.load(this.facilityChild.id)
        );
    }
}
