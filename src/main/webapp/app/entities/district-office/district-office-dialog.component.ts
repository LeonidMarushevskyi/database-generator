import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DistrictOffice } from './district-office.model';
import { DistrictOfficePopupService } from './district-office-popup.service';
import { DistrictOfficeService } from './district-office.service';

@Component({
    selector: 'jhi-district-office-dialog',
    templateUrl: './district-office-dialog.component.html'
})
export class DistrictOfficeDialogComponent implements OnInit {

    districtOffice: DistrictOffice;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private districtOfficeService: DistrictOfficeService,
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
        if (this.districtOffice.id !== undefined) {
            this.districtOfficeService.update(this.districtOffice)
                .subscribe((res: DistrictOffice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.districtOfficeService.create(this.districtOffice)
                .subscribe((res: DistrictOffice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: DistrictOffice) {
        this.eventManager.broadcast({ name: 'districtOfficeListModification', content: 'OK'});
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
    selector: 'jhi-district-office-popup',
    template: ''
})
export class DistrictOfficePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private districtOfficePopupService: DistrictOfficePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.districtOfficePopupService
                    .open(DistrictOfficeDialogComponent, params['id']);
            } else {
                this.modalRef = this.districtOfficePopupService
                    .open(DistrictOfficeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
