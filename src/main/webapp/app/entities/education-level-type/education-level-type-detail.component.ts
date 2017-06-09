import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { EducationLevelType } from './education-level-type.model';
import { EducationLevelTypeService } from './education-level-type.service';

@Component({
    selector: 'jhi-education-level-type-detail',
    templateUrl: './education-level-type-detail.component.html'
})
export class EducationLevelTypeDetailComponent implements OnInit, OnDestroy {

    educationLevelType: EducationLevelType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private educationLevelTypeService: EducationLevelTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEducationLevelTypes();
    }

    load(id) {
        this.educationLevelTypeService.find(id).subscribe((educationLevelType) => {
            this.educationLevelType = educationLevelType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEducationLevelTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'educationLevelTypeListModification',
            (response) => this.load(this.educationLevelType.id)
        );
    }
}
