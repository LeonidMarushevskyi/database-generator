import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { EducationLevelType } from './education-level-type.model';
import { EducationLevelTypePopupService } from './education-level-type-popup.service';
import { EducationLevelTypeService } from './education-level-type.service';

@Component({
    selector: 'jhi-education-level-type-delete-dialog',
    templateUrl: './education-level-type-delete-dialog.component.html'
})
export class EducationLevelTypeDeleteDialogComponent {

    educationLevelType: EducationLevelType;

    constructor(
        private educationLevelTypeService: EducationLevelTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.educationLevelTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'educationLevelTypeListModification',
                content: 'Deleted an educationLevelType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-education-level-type-delete-popup',
    template: ''
})
export class EducationLevelTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private educationLevelTypePopupService: EducationLevelTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.educationLevelTypePopupService
                .open(EducationLevelTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
