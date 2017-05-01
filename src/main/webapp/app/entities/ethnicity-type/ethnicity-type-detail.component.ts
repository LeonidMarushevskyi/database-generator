import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EthnicityType } from './ethnicity-type.model';
import { EthnicityTypeService } from './ethnicity-type.service';

@Component({
    selector: 'jhi-ethnicity-type-detail',
    templateUrl: './ethnicity-type-detail.component.html'
})
export class EthnicityTypeDetailComponent implements OnInit, OnDestroy {

    ethnicityType: EthnicityType;
    private subscription: any;

    constructor(
        private ethnicityTypeService: EthnicityTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.ethnicityTypeService.find(id).subscribe(ethnicityType => {
            this.ethnicityType = ethnicityType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
