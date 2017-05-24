import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AddressType } from './address-type.model';
import { AddressTypePopupService } from './address-type-popup.service';
import { AddressTypeService } from './address-type.service';

@Component({
    selector: 'jhi-address-type-dialog',
    templateUrl: './address-type-dialog.component.html'
})
export class AddressTypeDialogComponent implements OnInit {

    addressType: AddressType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private addressTypeService: AddressTypeService,
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
        if (this.addressType.id !== undefined) {
            this.addressTypeService.update(this.addressType)
                .subscribe((res: AddressType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.addressTypeService.create(this.addressType)
                .subscribe((res: AddressType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: AddressType) {
        this.eventManager.broadcast({ name: 'addressTypeListModification', content: 'OK'});
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
    selector: 'jhi-address-type-popup',
    template: ''
})
export class AddressTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private addressTypePopupService: AddressTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.addressTypePopupService
                    .open(AddressTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.addressTypePopupService
                    .open(AddressTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
