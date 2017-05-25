import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AssignedWorker } from './assigned-worker.model';
import { AssignedWorkerPopupService } from './assigned-worker-popup.service';
import { AssignedWorkerService } from './assigned-worker.service';

@Component({
    selector: 'jhi-assigned-worker-delete-dialog',
    templateUrl: './assigned-worker-delete-dialog.component.html'
})
export class AssignedWorkerDeleteDialogComponent {

    assignedWorker: AssignedWorker;

    constructor(
        private assignedWorkerService: AssignedWorkerService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.assignedWorkerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'assignedWorkerListModification',
                content: 'Deleted an assignedWorker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-assigned-worker-delete-popup',
    template: ''
})
export class AssignedWorkerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assignedWorkerPopupService: AssignedWorkerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.assignedWorkerPopupService
                .open(AssignedWorkerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
