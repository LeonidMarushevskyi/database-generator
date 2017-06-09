import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { DeterminedChild } from './determined-child.model';
import { DeterminedChildService } from './determined-child.service';

@Component({
    selector: 'jhi-determined-child-detail',
    templateUrl: './determined-child-detail.component.html'
})
export class DeterminedChildDetailComponent implements OnInit, OnDestroy {

    determinedChild: DeterminedChild;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private determinedChildService: DeterminedChildService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDeterminedChildren();
    }

    load(id) {
        this.determinedChildService.find(id).subscribe((determinedChild) => {
            this.determinedChild = determinedChild;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDeterminedChildren() {
        this.eventSubscriber = this.eventManager.subscribe(
            'determinedChildListModification',
            (response) => this.load(this.determinedChild.id)
        );
    }
}
