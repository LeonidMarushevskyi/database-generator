import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PhoneType } from './phone-type.model';
import { PhoneTypePopupService } from './phone-type-popup.service';
import { PhoneTypeService } from './phone-type.service';

@Component({
    selector: 'jhi-phone-type-dialog',
    templateUrl: './phone-type-dialog.component.html'
})
export class PhoneTypeDialogComponent implements OnInit {

    phoneType: PhoneType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private phoneTypeService: PhoneTypeService,
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
        if (this.phoneType.id !== undefined) {
            this.phoneTypeService.update(this.phoneType)
                .subscribe((res: PhoneType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.phoneTypeService.create(this.phoneType)
                .subscribe((res: PhoneType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: PhoneType) {
        this.eventManager.broadcast({ name: 'phoneTypeListModification', content: 'OK'});
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
    selector: 'jhi-phone-type-popup',
    template: ''
})
export class PhoneTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private phoneTypePopupService: PhoneTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.phoneTypePopupService
                    .open(PhoneTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.phoneTypePopupService
                    .open(PhoneTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
