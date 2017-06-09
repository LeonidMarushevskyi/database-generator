import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AppRelHistory } from './app-rel-history.model';
import { AppRelHistoryService } from './app-rel-history.service';

@Component({
    selector: 'jhi-app-rel-history-detail',
    templateUrl: './app-rel-history-detail.component.html'
})
export class AppRelHistoryDetailComponent implements OnInit, OnDestroy {

    appRelHistory: AppRelHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private appRelHistoryService: AppRelHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAppRelHistories();
    }

    load(id) {
        this.appRelHistoryService.find(id).subscribe((appRelHistory) => {
            this.appRelHistory = appRelHistory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAppRelHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'appRelHistoryListModification',
            (response) => this.load(this.appRelHistory.id)
        );
    }
}
