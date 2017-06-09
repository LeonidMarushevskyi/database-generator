import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Applicant } from './applicant.model';
import { ApplicantService } from './applicant.service';

@Component({
    selector: 'jhi-applicant-detail',
    templateUrl: './applicant-detail.component.html'
})
export class ApplicantDetailComponent implements OnInit, OnDestroy {

    applicant: Applicant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private applicantService: ApplicantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInApplicants();
    }

    load(id) {
        this.applicantService.find(id).subscribe((applicant) => {
            this.applicant = applicant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInApplicants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'applicantListModification',
            (response) => this.load(this.applicant.id)
        );
    }
}
