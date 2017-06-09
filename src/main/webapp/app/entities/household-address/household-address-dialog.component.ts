import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { HouseholdAddress } from './household-address.model';
import { HouseholdAddressPopupService } from './household-address-popup.service';
import { HouseholdAddressService } from './household-address.service';
import { Address, AddressService } from '../address';
import { AddressType, AddressTypeService } from '../address-type';
import { PosessionType, PosessionTypeService } from '../posession-type';
import { Household, HouseholdService } from '../household';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-household-address-dialog',
    templateUrl: './household-address-dialog.component.html'
})
export class HouseholdAddressDialogComponent implements OnInit {

    householdAddress: HouseholdAddress;
    authorities: any[];
    isSaving: boolean;

    addresses: Address[];

    addresstypes: AddressType[];

    posessiontypes: PosessionType[];

    households: Household[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private householdAddressService: HouseholdAddressService,
        private addressService: AddressService,
        private addressTypeService: AddressTypeService,
        private posessionTypeService: PosessionTypeService,
        private householdService: HouseholdService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.addressService
            .query({filter: 'householdaddress-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.householdAddress.address || !this.householdAddress.address.id) {
                    this.addresses = res.json;
                } else {
                    this.addressService
                        .find(this.householdAddress.address.id)
                        .subscribe((subRes: Address) => {
                            this.addresses = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.addressTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.addresstypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.posessionTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.posessiontypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.householdService.query()
            .subscribe((res: ResponseWrapper) => { this.households = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.householdAddress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.householdAddressService.update(this.householdAddress));
        } else {
            this.subscribeToSaveResponse(
                this.householdAddressService.create(this.householdAddress));
        }
    }

    private subscribeToSaveResponse(result: Observable<HouseholdAddress>) {
        result.subscribe((res: HouseholdAddress) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: HouseholdAddress) {
        this.eventManager.broadcast({ name: 'householdAddressListModification', content: 'OK'});
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

    trackAddressTypeById(index: number, item: AddressType) {
        return item.id;
    }

    trackPosessionTypeById(index: number, item: PosessionType) {
        return item.id;
    }

    trackHouseholdById(index: number, item: Household) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-household-address-popup',
    template: ''
})
export class HouseholdAddressPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private householdAddressPopupService: HouseholdAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.householdAddressPopupService
                    .open(HouseholdAddressDialogComponent, params['id']);
            } else {
                this.modalRef = this.householdAddressPopupService
                    .open(HouseholdAddressDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
