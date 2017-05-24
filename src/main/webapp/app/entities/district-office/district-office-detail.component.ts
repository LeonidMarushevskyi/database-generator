import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DistrictOffice } from './district-office.model';
import { DistrictOfficeService } from './district-office.service';

@Component({
    selector: 'jhi-district-office-detail',
    templateUrl: './district-office-detail.component.html'
})
export class DistrictOfficeDetailComponent implements OnInit, OnDestroy {

    districtOffice: DistrictOffice;
    private subscription: any;

    constructor(
        private districtOfficeService: DistrictOfficeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.districtOfficeService.find(id).subscribe(districtOffice => {
            this.districtOffice = districtOffice;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
