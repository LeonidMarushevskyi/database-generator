import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PhoneNumber } from './phone-number.model';
import { PhoneNumberPopupService } from './phone-number-popup.service';
import { PhoneNumberService } from './phone-number.service';
import { Employer, EmployerService } from '../employer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-phone-number-dialog',
    templateUrl: './phone-number-dialog.component.html'
})
export class PhoneNumberDialogComponent implements OnInit {

    phoneNumber: PhoneNumber;
    authorities: any[];
    isSaving: boolean;

    employers: Employer[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private phoneNumberService: PhoneNumberService,
        private employerService: EmployerService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.employerService.query()
            .subscribe((res: ResponseWrapper) => { this.employers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.phoneNumber.id !== undefined) {
            this.subscribeToSaveResponse(
                this.phoneNumberService.update(this.phoneNumber));
        } else {
            this.subscribeToSaveResponse(
                this.phoneNumberService.create(this.phoneNumber));
        }
    }

    private subscribeToSaveResponse(result: Observable<PhoneNumber>) {
        result.subscribe((res: PhoneNumber) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PhoneNumber) {
        this.eventManager.broadcast({ name: 'phoneNumberListModification', content: 'OK'});
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

    trackEmployerById(index: number, item: Employer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-phone-number-popup',
    template: ''
})
export class PhoneNumberPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private phoneNumberPopupService: PhoneNumberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.phoneNumberPopupService
                    .open(PhoneNumberDialogComponent, params['id']);
            } else {
                this.modalRef = this.phoneNumberPopupService
                    .open(PhoneNumberDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
