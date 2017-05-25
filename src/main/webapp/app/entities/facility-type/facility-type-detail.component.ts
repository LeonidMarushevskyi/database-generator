import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { FacilityType } from './facility-type.model';
import { FacilityTypeService } from './facility-type.service';

@Component({
    selector: 'jhi-facility-type-detail',
    templateUrl: './facility-type-detail.component.html'
})
export class FacilityTypeDetailComponent implements OnInit, OnDestroy {

    facilityType: FacilityType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private facilityTypeService: FacilityTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacilityTypes();
    }

    load(id) {
        this.facilityTypeService.find(id).subscribe((facilityType) => {
            this.facilityType = facilityType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacilityTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facilityTypeListModification',
            (response) => this.load(this.facilityType.id)
        );
    }
}
