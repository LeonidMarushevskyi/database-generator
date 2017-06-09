import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AgeGroupType } from './age-group-type.model';
import { AgeGroupTypePopupService } from './age-group-type-popup.service';
import { AgeGroupTypeService } from './age-group-type.service';

@Component({
    selector: 'jhi-age-group-type-dialog',
    templateUrl: './age-group-type-dialog.component.html'
})
export class AgeGroupTypeDialogComponent implements OnInit {

    ageGroupType: AgeGroupType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private ageGroupTypeService: AgeGroupTypeService,
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
        if (this.ageGroupType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ageGroupTypeService.update(this.ageGroupType));
        } else {
            this.subscribeToSaveResponse(
                this.ageGroupTypeService.create(this.ageGroupType));
        }
    }

    private subscribeToSaveResponse(result: Observable<AgeGroupType>) {
        result.subscribe((res: AgeGroupType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AgeGroupType) {
        this.eventManager.broadcast({ name: 'ageGroupTypeListModification', content: 'OK'});
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
    selector: 'jhi-age-group-type-popup',
    template: ''
})
export class AgeGroupTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ageGroupTypePopupService: AgeGroupTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.ageGroupTypePopupService
                    .open(AgeGroupTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.ageGroupTypePopupService
                    .open(AgeGroupTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
