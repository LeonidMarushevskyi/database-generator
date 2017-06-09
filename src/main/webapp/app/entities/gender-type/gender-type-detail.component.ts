import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { GenderType } from './gender-type.model';
import { GenderTypeService } from './gender-type.service';

@Component({
    selector: 'jhi-gender-type-detail',
    templateUrl: './gender-type-detail.component.html'
})
export class GenderTypeDetailComponent implements OnInit, OnDestroy {

    genderType: GenderType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private genderTypeService: GenderTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGenderTypes();
    }

    load(id) {
        this.genderTypeService.find(id).subscribe((genderType) => {
            this.genderType = genderType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGenderTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'genderTypeListModification',
            (response) => this.load(this.genderType.id)
        );
    }
}
