import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PhoneNumberType } from './phone-number-type.model';
import { PhoneNumberTypePopupService } from './phone-number-type-popup.service';
import { PhoneNumberTypeService } from './phone-number-type.service';

@Component({
    selector: 'jhi-phone-number-type-dialog',
    templateUrl: './phone-number-type-dialog.component.html'
})
export class PhoneNumberTypeDialogComponent implements OnInit {

    phoneNumberType: PhoneNumberType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private phoneNumberTypeService: PhoneNumberTypeService,
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
        if (this.phoneNumberType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.phoneNumberTypeService.update(this.phoneNumberType));
        } else {
            this.subscribeToSaveResponse(
                this.phoneNumberTypeService.create(this.phoneNumberType));
        }
    }

    private subscribeToSaveResponse(result: Observable<PhoneNumberType>) {
        result.subscribe((res: PhoneNumberType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PhoneNumberType) {
        this.eventManager.broadcast({ name: 'phoneNumberTypeListModification', content: 'OK'});
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
    selector: 'jhi-phone-number-type-popup',
    template: ''
})
export class PhoneNumberTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private phoneNumberTypePopupService: PhoneNumberTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.phoneNumberTypePopupService
                    .open(PhoneNumberTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.phoneNumberTypePopupService
                    .open(PhoneNumberTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
