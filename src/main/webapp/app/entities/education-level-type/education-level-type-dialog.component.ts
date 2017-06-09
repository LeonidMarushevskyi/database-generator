import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { EducationLevelType } from './education-level-type.model';
import { EducationLevelTypePopupService } from './education-level-type-popup.service';
import { EducationLevelTypeService } from './education-level-type.service';

@Component({
    selector: 'jhi-education-level-type-dialog',
    templateUrl: './education-level-type-dialog.component.html'
})
export class EducationLevelTypeDialogComponent implements OnInit {

    educationLevelType: EducationLevelType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private educationLevelTypeService: EducationLevelTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.educationLevelType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.educationLevelTypeService.update(this.educationLevelType));
        } else {
            this.subscribeToSaveResponse(
                this.educationLevelTypeService.create(this.educationLevelType));
        }
    }

    private subscribeToSaveResponse(result: Observable<EducationLevelType>) {
        result.subscribe((res: EducationLevelType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EducationLevelType) {
        this.eventManager.broadcast({ name: 'educationLevelTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-education-level-type-popup',
    template: ''
})
export class EducationLevelTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private educationLevelTypePopupService: EducationLevelTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.educationLevelTypePopupService
                    .open(EducationLevelTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.educationLevelTypePopupService
                    .open(EducationLevelTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
