import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Employment } from './employment.model';
import { EmploymentService } from './employment.service';

@Component({
    selector: 'jhi-employment-detail',
    templateUrl: './employment-detail.component.html'
})
export class EmploymentDetailComponent implements OnInit, OnDestroy {

    employment: Employment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private employmentService: EmploymentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployments();
    }

    load(id) {
        this.employmentService.find(id).subscribe((employment) => {
            this.employment = employment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employmentListModification',
            (response) => this.load(this.employment.id)
        );
    }
}
