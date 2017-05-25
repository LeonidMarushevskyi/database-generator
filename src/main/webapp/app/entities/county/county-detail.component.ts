import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { County } from './county.model';
import { CountyService } from './county.service';

@Component({
    selector: 'jhi-county-detail',
    templateUrl: './county-detail.component.html'
})
export class CountyDetailComponent implements OnInit, OnDestroy {

    county: County;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private countyService: CountyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCounties();
    }

    load(id) {
        this.countyService.find(id).subscribe((county) => {
            this.county = county;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCounties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'countyListModification',
            (response) => this.load(this.county.id)
        );
    }
}
