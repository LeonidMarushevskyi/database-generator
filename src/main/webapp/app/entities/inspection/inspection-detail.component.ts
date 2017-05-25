import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Inspection } from './inspection.model';
import { InspectionService } from './inspection.service';

@Component({
    selector: 'jhi-inspection-detail',
    templateUrl: './inspection-detail.component.html'
})
export class InspectionDetailComponent implements OnInit, OnDestroy {

    inspection: Inspection;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private inspectionService: InspectionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInspections();
    }

    load(id) {
        this.inspectionService.find(id).subscribe((inspection) => {
            this.inspection = inspection;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInspections() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inspectionListModification',
            (response) => this.load(this.inspection.id)
        );
    }
}
