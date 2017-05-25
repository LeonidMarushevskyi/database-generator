import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { DistrictOffice } from './district-office.model';
import { DistrictOfficeService } from './district-office.service';

@Component({
    selector: 'jhi-district-office-detail',
    templateUrl: './district-office-detail.component.html'
})
export class DistrictOfficeDetailComponent implements OnInit, OnDestroy {

    districtOffice: DistrictOffice;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private districtOfficeService: DistrictOfficeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDistrictOffices();
    }

    load(id) {
        this.districtOfficeService.find(id).subscribe((districtOffice) => {
            this.districtOffice = districtOffice;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDistrictOffices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'districtOfficeListModification',
            (response) => this.load(this.districtOffice.id)
        );
    }
}
