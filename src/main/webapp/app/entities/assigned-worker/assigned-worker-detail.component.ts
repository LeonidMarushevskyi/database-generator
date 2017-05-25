import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AssignedWorker } from './assigned-worker.model';
import { AssignedWorkerService } from './assigned-worker.service';

@Component({
    selector: 'jhi-assigned-worker-detail',
    templateUrl: './assigned-worker-detail.component.html'
})
export class AssignedWorkerDetailComponent implements OnInit, OnDestroy {

    assignedWorker: AssignedWorker;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private assignedWorkerService: AssignedWorkerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAssignedWorkers();
    }

    load(id) {
        this.assignedWorkerService.find(id).subscribe((assignedWorker) => {
            this.assignedWorker = assignedWorker;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAssignedWorkers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'assignedWorkerListModification',
            (response) => this.load(this.assignedWorker.id)
        );
    }
}
