import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { EducationPoint } from './education-point.model';
import { EducationPointService } from './education-point.service';

@Component({
    selector: 'jhi-education-point-detail',
    templateUrl: './education-point-detail.component.html'
})
export class EducationPointDetailComponent implements OnInit, OnDestroy {

    educationPoint: EducationPoint;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private educationPointService: EducationPointService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEducationPoints();
    }

    load(id) {
        this.educationPointService.find(id).subscribe((educationPoint) => {
            this.educationPoint = educationPoint;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEducationPoints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'educationPointListModification',
            (response) => this.load(this.educationPoint.id)
        );
    }
}
