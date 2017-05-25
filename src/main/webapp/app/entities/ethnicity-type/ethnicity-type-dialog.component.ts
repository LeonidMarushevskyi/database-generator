import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { EthnicityType } from './ethnicity-type.model';
import { EthnicityTypePopupService } from './ethnicity-type-popup.service';
import { EthnicityTypeService } from './ethnicity-type.service';

@Component({
    selector: 'jhi-ethnicity-type-dialog',
    templateUrl: './ethnicity-type-dialog.component.html'
})
export class EthnicityTypeDialogComponent implements OnInit {

    ethnicityType: EthnicityType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private ethnicityTypeService: EthnicityTypeService,
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
        if (this.ethnicityType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ethnicityTypeService.update(this.ethnicityType));
        } else {
            this.subscribeToSaveResponse(
                this.ethnicityTypeService.create(this.ethnicityType));
        }
    }

    private subscribeToSaveResponse(result: Observable<EthnicityType>) {
        result.subscribe((res: EthnicityType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EthnicityType) {
        this.eventManager.broadcast({ name: 'ethnicityTypeListModification', content: 'OK'});
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
    selector: 'jhi-ethnicity-type-popup',
    template: ''
})
export class EthnicityTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ethnicityTypePopupService: EthnicityTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.ethnicityTypePopupService
                    .open(EthnicityTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.ethnicityTypePopupService
                    .open(EthnicityTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
