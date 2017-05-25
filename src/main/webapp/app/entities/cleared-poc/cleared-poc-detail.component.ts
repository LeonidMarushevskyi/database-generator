import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ClearedPOC } from './cleared-poc.model';
import { ClearedPOCService } from './cleared-poc.service';

@Component({
    selector: 'jhi-cleared-poc-detail',
    templateUrl: './cleared-poc-detail.component.html'
})
export class ClearedPOCDetailComponent implements OnInit, OnDestroy {

    clearedPOC: ClearedPOC;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private clearedPOCService: ClearedPOCService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClearedPOCS();
    }

    load(id) {
        this.clearedPOCService.find(id).subscribe((clearedPOC) => {
            this.clearedPOC = clearedPOC;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClearedPOCS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clearedPOCListModification',
            (response) => this.load(this.clearedPOC.id)
        );
    }
}
