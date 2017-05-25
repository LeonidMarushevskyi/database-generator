import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Complaint } from './complaint.model';
import { ComplaintService } from './complaint.service';

@Component({
    selector: 'jhi-complaint-detail',
    templateUrl: './complaint-detail.component.html'
})
export class ComplaintDetailComponent implements OnInit, OnDestroy {

    complaint: Complaint;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private complaintService: ComplaintService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInComplaints();
    }

    load(id) {
        this.complaintService.find(id).subscribe((complaint) => {
            this.complaint = complaint;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInComplaints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'complaintListModification',
            (response) => this.load(this.complaint.id)
        );
    }
}
