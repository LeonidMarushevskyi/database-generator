import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FacilityAddress } from './facility-address.model';
import { FacilityAddressPopupService } from './facility-address-popup.service';
import { FacilityAddressService } from './facility-address.service';
import { Facility, FacilityService } from '../facility';
import { Address, AddressService } from '../address';
import { AddressType, AddressTypeService } from '../address-type';

@Component({
    selector: 'jhi-facility-address-dialog',
    templateUrl: './facility-address-dialog.component.html'
})
export class FacilityAddressDialogComponent implements OnInit {

    facilityAddress: FacilityAddress;
    authorities: any[];
    isSaving: boolean;

    facilities: Facility[];

    addresses: Address[];

    addresstypes: AddressType[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private facilityAddressService: FacilityAddressService,
        private facilityService: FacilityService,
        private addressService: AddressService,
        private addressTypeService: AddressTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.facilityService.query().subscribe(
            (res: Response) => { this.facilities = res.json(); }, (res: Response) => this.onError(res.json()));
        this.addressService.query({filter: 'facilityaddress-is-null'}).subscribe((res: Response) => {
            if (!this.facilityAddress.addressId) {
                this.addresses = res.json();
            } else {
                this.addressService.find(this.facilityAddress.addressId).subscribe((subRes: Address) => {
                    this.addresses = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.addressTypeService.query().subscribe(
            (res: Response) => { this.addresstypes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.facilityAddress.id !== undefined) {
            this.facilityAddressService.update(this.facilityAddress)
                .subscribe((res: FacilityAddress) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.facilityAddressService.create(this.facilityAddress)
                .subscribe((res: FacilityAddress) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: FacilityAddress) {
        this.eventManager.broadcast({ name: 'facilityAddressListModification', content: 'OK'});
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

    trackAddressById(index: number, item: Address) {
        return item.id;
    }

    trackAddressTypeById(index: number, item: AddressType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-facility-address-popup',
    template: ''
})
export class FacilityAddressPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private facilityAddressPopupService: FacilityAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.facilityAddressPopupService
                    .open(FacilityAddressDialogComponent, params['id']);
            } else {
                this.modalRef = this.facilityAddressPopupService
                    .open(FacilityAddressDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
