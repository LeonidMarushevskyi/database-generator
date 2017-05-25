import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PersonAddress } from './person-address.model';
import { PersonAddressPopupService } from './person-address-popup.service';
import { PersonAddressService } from './person-address.service';
import { Person, PersonService } from '../person';
import { Address, AddressService } from '../address';
import { AddressType, AddressTypeService } from '../address-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-address-dialog',
    templateUrl: './person-address-dialog.component.html'
})
export class PersonAddressDialogComponent implements OnInit {

    personAddress: PersonAddress;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    races: Address[];

    addresstypes: AddressType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personAddressService: PersonAddressService,
        private personService: PersonService,
        private addressService: AddressService,
        private addressTypeService: AddressTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.addressService
            .query({filter: 'personaddress-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personAddress.raceId) {
                    this.races = res.json;
                } else {
                    this.addressService
                        .find(this.personAddress.raceId)
                        .subscribe((subRes: Address) => {
                            this.races = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.addressTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.addresstypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personAddress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personAddressService.update(this.personAddress));
        } else {
            this.subscribeToSaveResponse(
                this.personAddressService.create(this.personAddress));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonAddress>) {
        result.subscribe((res: PersonAddress) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonAddress) {
        this.eventManager.broadcast({ name: 'personAddressListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
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
    selector: 'jhi-person-address-popup',
    template: ''
})
export class PersonAddressPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personAddressPopupService: PersonAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personAddressPopupService
                    .open(PersonAddressDialogComponent, params['id']);
            } else {
                this.modalRef = this.personAddressPopupService
                    .open(PersonAddressDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
