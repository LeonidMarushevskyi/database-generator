import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { EmailAddress } from './email-address.model';
import { EmailAddressPopupService } from './email-address-popup.service';
import { EmailAddressService } from './email-address.service';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-email-address-dialog',
    templateUrl: './email-address-dialog.component.html'
})
export class EmailAddressDialogComponent implements OnInit {

    emailAddress: EmailAddress;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private emailAddressService: EmailAddressService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.emailAddress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.emailAddressService.update(this.emailAddress));
        } else {
            this.subscribeToSaveResponse(
                this.emailAddressService.create(this.emailAddress));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmailAddress>) {
        result.subscribe((res: EmailAddress) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EmailAddress) {
        this.eventManager.broadcast({ name: 'emailAddressListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-email-address-popup',
    template: ''
})
export class EmailAddressPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailAddressPopupService: EmailAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.emailAddressPopupService
                    .open(EmailAddressDialogComponent, params['id']);
            } else {
                this.modalRef = this.emailAddressPopupService
                    .open(EmailAddressDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
