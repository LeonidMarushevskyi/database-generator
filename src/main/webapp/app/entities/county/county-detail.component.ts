import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { County } from './county.model';
import { CountyService } from './county.service';

@Component({
    selector: 'jhi-county-detail',
    templateUrl: './county-detail.component.html'
})
export class CountyDetailComponent implements OnInit, OnDestroy {

    county: County;
    private subscription: any;

    constructor(
        private countyService: CountyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.countyService.find(id).subscribe(county => {
            this.county = county;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
