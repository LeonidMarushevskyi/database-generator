import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Applicant } from './applicant.model';
import { ApplicantPopupService } from './applicant-popup.service';
import { ApplicantService } from './applicant.service';

@Component({
    selector: 'jhi-applicant-delete-dialog',
    templateUrl: './applicant-delete-dialog.component.html'
})
export class ApplicantDeleteDialogComponent {

    applicant: Applicant;

    constructor(
        private applicantService: ApplicantService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.applicantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'applicantListModification',
                content: 'Deleted an applicant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-applicant-delete-popup',
    template: ''
})
export class ApplicantDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private applicantPopupService: ApplicantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.applicantPopupService
                .open(ApplicantDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
