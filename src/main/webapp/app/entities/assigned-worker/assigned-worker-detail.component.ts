import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AssignedWorker } from './assigned-worker.model';
import { AssignedWorkerService } from './assigned-worker.service';

@Component({
    selector: 'jhi-assigned-worker-detail',
    templateUrl: './assigned-worker-detail.component.html'
})
export class AssignedWorkerDetailComponent implements OnInit, OnDestroy {

    assignedWorker: AssignedWorker;
    private subscription: any;

    constructor(
        private assignedWorkerService: AssignedWorkerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.assignedWorkerService.find(id).subscribe(assignedWorker => {
            this.assignedWorker = assignedWorker;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
