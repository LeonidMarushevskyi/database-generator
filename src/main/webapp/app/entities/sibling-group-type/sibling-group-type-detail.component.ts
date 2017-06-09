import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { SiblingGroupType } from './sibling-group-type.model';
import { SiblingGroupTypeService } from './sibling-group-type.service';

@Component({
    selector: 'jhi-sibling-group-type-detail',
    templateUrl: './sibling-group-type-detail.component.html'
})
export class SiblingGroupTypeDetailComponent implements OnInit, OnDestroy {

    siblingGroupType: SiblingGroupType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private siblingGroupTypeService: SiblingGroupTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSiblingGroupTypes();
    }

    load(id) {
        this.siblingGroupTypeService.find(id).subscribe((siblingGroupType) => {
            this.siblingGroupType = siblingGroupType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSiblingGroupTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'siblingGroupTypeListModification',
            (response) => this.load(this.siblingGroupType.id)
        );
    }
}
