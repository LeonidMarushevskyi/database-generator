import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Employment } from './employment.model';
import { EmploymentPopupService } from './employment-popup.service';
import { EmploymentService } from './employment.service';

@Component({
    selector: 'jhi-employment-delete-dialog',
    templateUrl: './employment-delete-dialog.component.html'
})
export class EmploymentDeleteDialogComponent {

    employment: Employment;

    constructor(
        private employmentService: EmploymentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employmentListModification',
                content: 'Deleted an employment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employment-delete-popup',
    template: ''
})
export class EmploymentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employmentPopupService: EmploymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.employmentPopupService
                .open(EmploymentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
