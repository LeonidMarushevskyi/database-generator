import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { BodyOfWater } from './body-of-water.model';
import { BodyOfWaterService } from './body-of-water.service';

@Component({
    selector: 'jhi-body-of-water-detail',
    templateUrl: './body-of-water-detail.component.html'
})
export class BodyOfWaterDetailComponent implements OnInit, OnDestroy {

    bodyOfWater: BodyOfWater;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private bodyOfWaterService: BodyOfWaterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBodyOfWaters();
    }

    load(id) {
        this.bodyOfWaterService.find(id).subscribe((bodyOfWater) => {
            this.bodyOfWater = bodyOfWater;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBodyOfWaters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bodyOfWaterListModification',
            (response) => this.load(this.bodyOfWater.id)
        );
    }
}
