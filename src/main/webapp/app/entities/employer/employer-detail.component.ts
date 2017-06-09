import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Employer } from './employer.model';
import { EmployerService } from './employer.service';

@Component({
    selector: 'jhi-employer-detail',
    templateUrl: './employer-detail.component.html'
})
export class EmployerDetailComponent implements OnInit, OnDestroy {

    employer: Employer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private employerService: EmployerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployers();
    }

    load(id) {
        this.employerService.find(id).subscribe((employer) => {
            this.employer = employer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employerListModification',
            (response) => this.load(this.employer.id)
        );
    }
}
