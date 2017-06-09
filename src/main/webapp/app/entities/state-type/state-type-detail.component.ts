import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { StateType } from './state-type.model';
import { StateTypeService } from './state-type.service';

@Component({
    selector: 'jhi-state-type-detail',
    templateUrl: './state-type-detail.component.html'
})
export class StateTypeDetailComponent implements OnInit, OnDestroy {

    stateType: StateType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private stateTypeService: StateTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStateTypes();
    }

    load(id) {
        this.stateTypeService.find(id).subscribe((stateType) => {
            this.stateType = stateType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStateTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'stateTypeListModification',
            (response) => this.load(this.stateType.id)
        );
    }
}
