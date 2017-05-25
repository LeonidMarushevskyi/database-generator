import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Complaint } from './complaint.model';
import { ComplaintPopupService } from './complaint-popup.service';
import { ComplaintService } from './complaint.service';

@Component({
    selector: 'jhi-complaint-delete-dialog',
    templateUrl: './complaint-delete-dialog.component.html'
})
export class ComplaintDeleteDialogComponent {

    complaint: Complaint;

    constructor(
        private complaintService: ComplaintService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.complaintService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'complaintListModification',
                content: 'Deleted an complaint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-complaint-delete-popup',
    template: ''
})
export class ComplaintDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private complaintPopupService: ComplaintPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.complaintPopupService
                .open(ComplaintDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
