import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FacilityPhone } from './facility-phone.model';
import { FacilityPhonePopupService } from './facility-phone-popup.service';
import { FacilityPhoneService } from './facility-phone.service';
import { Facility, FacilityService } from '../facility';
import { Phone, PhoneService } from '../phone';
import { PhoneType, PhoneTypeService } from '../phone-type';

@Component({
    selector: 'jhi-facility-phone-dialog',
    templateUrl: './facility-phone-dialog.component.html'
})
export class FacilityPhoneDialogComponent implements OnInit {

    facilityPhone: FacilityPhone;
    authorities: any[];
    isSaving: boolean;

    facilities: Facility[];

    phones: Phone[];

    phonetypes: PhoneType[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private facilityPhoneService: FacilityPhoneService,
        private facilityService: FacilityService,
        private phoneService: PhoneService,
        private phoneTypeService: PhoneTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.facilityService.query().subscribe(
            (res: Response) => { this.facilities = res.json(); }, (res: Response) => this.onError(res.json()));
        this.phoneService.query({filter: 'facilityphone-is-null'}).subscribe((res: Response) => {
            if (!this.facilityPhone.phoneId) {
                this.phones = res.json();
            } else {
                this.phoneService.find(this.facilityPhone.phoneId).subscribe((subRes: Phone) => {
                    this.phones = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.phoneTypeService.query().subscribe(
            (res: Response) => { this.phonetypes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.facilityPhone.id !== undefined) {
            this.facilityPhoneService.update(this.facilityPhone)
                .subscribe((res: FacilityPhone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.facilityPhoneService.create(this.facilityPhone)
                .subscribe((res: FacilityPhone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: FacilityPhone) {
        this.eventManager.broadcast({ name: 'facilityPhoneListModification', content: 'OK'});
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

    trackFacilityById(index: number, item: Facility) {
        return item.id;
    }

    trackPhoneById(index: number, item: Phone) {
        return item.id;
    }

    trackPhoneTypeById(index: number, item: PhoneType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-facility-phone-popup',
    template: ''
})
export class FacilityPhonePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private facilityPhonePopupService: FacilityPhonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.facilityPhonePopupService
                    .open(FacilityPhoneDialogComponent, params['id']);
            } else {
                this.modalRef = this.facilityPhonePopupService
                    .open(FacilityPhoneDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
