import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { EthnicityType } from './ethnicity-type.model';
import { EthnicityTypeService } from './ethnicity-type.service';

@Component({
    selector: 'jhi-ethnicity-type-detail',
    templateUrl: './ethnicity-type-detail.component.html'
})
export class EthnicityTypeDetailComponent implements OnInit, OnDestroy {

    ethnicityType: EthnicityType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private ethnicityTypeService: EthnicityTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEthnicityTypes();
    }

    load(id) {
        this.ethnicityTypeService.find(id).subscribe((ethnicityType) => {
            this.ethnicityType = ethnicityType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEthnicityTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ethnicityTypeListModification',
            (response) => this.load(this.ethnicityType.id)
        );
    }
}
