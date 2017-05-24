import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PhoneType } from './phone-type.model';
import { PhoneTypeService } from './phone-type.service';

@Component({
    selector: 'jhi-phone-type-detail',
    templateUrl: './phone-type-detail.component.html'
})
export class PhoneTypeDetailComponent implements OnInit, OnDestroy {

    phoneType: PhoneType;
    private subscription: any;

    constructor(
        private phoneTypeService: PhoneTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.phoneTypeService.find(id).subscribe(phoneType => {
            this.phoneType = phoneType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
