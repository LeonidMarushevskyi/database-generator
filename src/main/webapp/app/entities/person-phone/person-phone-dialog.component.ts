import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PersonPhone } from './person-phone.model';
import { PersonPhonePopupService } from './person-phone-popup.service';
import { PersonPhoneService } from './person-phone.service';
import { PhoneNumber, PhoneNumberService } from '../phone-number';
import { PhoneNumberType, PhoneNumberTypeService } from '../phone-number-type';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-phone-dialog',
    templateUrl: './person-phone-dialog.component.html'
})
export class PersonPhoneDialogComponent implements OnInit {

    personPhone: PersonPhone;
    authorities: any[];
    isSaving: boolean;

    phonenumbers: PhoneNumber[];

    phonenumbertypes: PhoneNumberType[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personPhoneService: PersonPhoneService,
        private phoneNumberService: PhoneNumberService,
        private phoneNumberTypeService: PhoneNumberTypeService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.phoneNumberService
            .query({filter: 'personphone-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personPhone.phoneNumber || !this.personPhone.phoneNumber.id) {
                    this.phonenumbers = res.json;
                } else {
                    this.phoneNumberService
                        .find(this.personPhone.phoneNumber.id)
                        .subscribe((subRes: PhoneNumber) => {
                            this.phonenumbers = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.phoneNumberTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.phonenumbertypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personPhone.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personPhoneService.update(this.personPhone));
        } else {
            this.subscribeToSaveResponse(
                this.personPhoneService.create(this.personPhone));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonPhone>) {
        result.subscribe((res: PersonPhone) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonPhone) {
        this.eventManager.broadcast({ name: 'personPhoneListModification', content: 'OK'});
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

    trackPhoneNumberById(index: number, item: PhoneNumber) {
        return item.id;
    }

    trackPhoneNumberTypeById(index: number, item: PhoneNumberType) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-person-phone-popup',
    template: ''
})
export class PersonPhonePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personPhonePopupService: PersonPhonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personPhonePopupService
                    .open(PersonPhoneDialogComponent, params['id']);
            } else {
                this.modalRef = this.personPhonePopupService
                    .open(PersonPhoneDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
