import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Employer } from './employer.model';
import { EmployerPopupService } from './employer-popup.service';
import { EmployerService } from './employer.service';
import { Address, AddressService } from '../address';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employer-dialog',
    templateUrl: './employer-dialog.component.html'
})
export class EmployerDialogComponent implements OnInit {

    employer: Employer;
    authorities: any[];
    isSaving: boolean;

    addresses: Address[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private employerService: EmployerService,
        private addressService: AddressService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.addressService
            .query({filter: 'employer-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.employer.address || !this.employer.address.id) {
                    this.addresses = res.json;
                } else {
                    this.addressService
                        .find(this.employer.address.id)
                        .subscribe((subRes: Address) => {
                            this.addresses = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.employer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employerService.update(this.employer));
        } else {
            this.subscribeToSaveResponse(
                this.employerService.create(this.employer));
        }
    }

    private subscribeToSaveResponse(result: Observable<Employer>) {
        result.subscribe((res: Employer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Employer) {
        this.eventManager.broadcast({ name: 'employerListModification', content: 'OK'});
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

    trackAddressById(index: number, item: Address) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-employer-popup',
    template: ''
})
export class EmployerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employerPopupService: EmployerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.employerPopupService
                    .open(EmployerDialogComponent, params['id']);
            } else {
                this.modalRef = this.employerPopupService
                    .open(EmployerDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
