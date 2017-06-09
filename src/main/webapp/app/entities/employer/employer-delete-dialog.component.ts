import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Employer } from './employer.model';
import { EmployerPopupService } from './employer-popup.service';
import { EmployerService } from './employer.service';

@Component({
    selector: 'jhi-employer-delete-dialog',
    templateUrl: './employer-delete-dialog.component.html'
})
export class EmployerDeleteDialogComponent {

    employer: Employer;

    constructor(
        private employerService: EmployerService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employerListModification',
                content: 'Deleted an employer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employer-delete-popup',
    template: ''
})
export class EmployerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employerPopupService: EmployerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.employerPopupService
                .open(EmployerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
