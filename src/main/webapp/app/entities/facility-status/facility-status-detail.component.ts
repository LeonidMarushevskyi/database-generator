import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { FacilityStatus } from './facility-status.model';
import { FacilityStatusService } from './facility-status.service';

@Component({
    selector: 'jhi-facility-status-detail',
    templateUrl: './facility-status-detail.component.html'
})
export class FacilityStatusDetailComponent implements OnInit, OnDestroy {

    facilityStatus: FacilityStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private facilityStatusService: FacilityStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacilityStatuses();
    }

    load(id) {
        this.facilityStatusService.find(id).subscribe((facilityStatus) => {
            this.facilityStatus = facilityStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacilityStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facilityStatusListModification',
            (response) => this.load(this.facilityStatus.id)
        );
    }
}
