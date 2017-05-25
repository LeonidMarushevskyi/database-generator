import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PersonPhone } from './person-phone.model';
import { PersonPhonePopupService } from './person-phone-popup.service';
import { PersonPhoneService } from './person-phone.service';
import { Person, PersonService } from '../person';
import { Phone, PhoneService } from '../phone';
import { PhoneType, PhoneTypeService } from '../phone-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-phone-dialog',
    templateUrl: './person-phone-dialog.component.html'
})
export class PersonPhoneDialogComponent implements OnInit {

    personPhone: PersonPhone;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    phones: Phone[];

    phonetypes: PhoneType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personPhoneService: PersonPhoneService,
        private personService: PersonService,
        private phoneService: PhoneService,
        private phoneTypeService: PhoneTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.phoneService
            .query({filter: 'personphone-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personPhone.phoneId) {
                    this.phones = res.json;
                } else {
                    this.phoneService
                        .find(this.personPhone.phoneId)
                        .subscribe((subRes: Phone) => {
                            this.phones = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.phoneTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.phonetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    trackPersonById(index: number, item: Person) {
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
