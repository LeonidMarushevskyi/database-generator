import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FacilityStatus } from './facility-status.model';
import { FacilityStatusPopupService } from './facility-status-popup.service';
import { FacilityStatusService } from './facility-status.service';

@Component({
    selector: 'jhi-facility-status-dialog',
    templateUrl: './facility-status-dialog.component.html'
})
export class FacilityStatusDialogComponent implements OnInit {

    facilityStatus: FacilityStatus;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private facilityStatusService: FacilityStatusService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.facilityStatus.id !== undefined) {
            this.facilityStatusService.update(this.facilityStatus)
                .subscribe((res: FacilityStatus) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.facilityStatusService.create(this.facilityStatus)
                .subscribe((res: FacilityStatus) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: FacilityStatus) {
        this.eventManager.broadcast({ name: 'facilityStatusListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-facility-status-popup',
    template: ''
})
export class FacilityStatusPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private facilityStatusPopupService: FacilityStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.facilityStatusPopupService
                    .open(FacilityStatusDialogComponent, params['id']);
            } else {
                this.modalRef = this.facilityStatusPopupService
                    .open(FacilityStatusDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
