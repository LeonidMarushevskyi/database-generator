import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { LicensureHistory } from './licensure-history.model';
import { LicensureHistoryService } from './licensure-history.service';

@Component({
    selector: 'jhi-licensure-history-detail',
    templateUrl: './licensure-history-detail.component.html'
})
export class LicensureHistoryDetailComponent implements OnInit, OnDestroy {

    licensureHistory: LicensureHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private licensureHistoryService: LicensureHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLicensureHistories();
    }

    load(id) {
        this.licensureHistoryService.find(id).subscribe((licensureHistory) => {
            this.licensureHistory = licensureHistory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLicensureHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'licensureHistoryListModification',
            (response) => this.load(this.licensureHistory.id)
        );
    }
}
