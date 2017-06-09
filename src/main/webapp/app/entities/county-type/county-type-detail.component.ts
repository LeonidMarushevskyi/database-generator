import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { CountyType } from './county-type.model';
import { CountyTypeService } from './county-type.service';

@Component({
    selector: 'jhi-county-type-detail',
    templateUrl: './county-type-detail.component.html'
})
export class CountyTypeDetailComponent implements OnInit, OnDestroy {

    countyType: CountyType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private countyTypeService: CountyTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCountyTypes();
    }

    load(id) {
        this.countyTypeService.find(id).subscribe((countyType) => {
            this.countyType = countyType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCountyTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'countyTypeListModification',
            (response) => this.load(this.countyType.id)
        );
    }
}
