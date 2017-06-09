import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { EducationPoint } from './education-point.model';
import { EducationPointPopupService } from './education-point-popup.service';
import { EducationPointService } from './education-point.service';

@Component({
    selector: 'jhi-education-point-delete-dialog',
    templateUrl: './education-point-delete-dialog.component.html'
})
export class EducationPointDeleteDialogComponent {

    educationPoint: EducationPoint;

    constructor(
        private educationPointService: EducationPointService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.educationPointService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'educationPointListModification',
                content: 'Deleted an educationPoint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-education-point-delete-popup',
    template: ''
})
export class EducationPointDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private educationPointPopupService: EducationPointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.educationPointPopupService
                .open(EducationPointDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
