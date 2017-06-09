import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AgeGroupType } from './age-group-type.model';
import { AgeGroupTypeService } from './age-group-type.service';

@Component({
    selector: 'jhi-age-group-type-detail',
    templateUrl: './age-group-type-detail.component.html'
})
export class AgeGroupTypeDetailComponent implements OnInit, OnDestroy {

    ageGroupType: AgeGroupType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private ageGroupTypeService: AgeGroupTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAgeGroupTypes();
    }

    load(id) {
        this.ageGroupTypeService.find(id).subscribe((ageGroupType) => {
            this.ageGroupType = ageGroupType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAgeGroupTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ageGroupTypeListModification',
            (response) => this.load(this.ageGroupType.id)
        );
    }
}
