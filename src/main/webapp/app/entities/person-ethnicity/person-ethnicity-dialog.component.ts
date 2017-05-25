import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PersonEthnicity } from './person-ethnicity.model';
import { PersonEthnicityPopupService } from './person-ethnicity-popup.service';
import { PersonEthnicityService } from './person-ethnicity.service';
import { EthnicityType, EthnicityTypeService } from '../ethnicity-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-ethnicity-dialog',
    templateUrl: './person-ethnicity-dialog.component.html'
})
export class PersonEthnicityDialogComponent implements OnInit {

    personEthnicity: PersonEthnicity;
    authorities: any[];
    isSaving: boolean;

    ethnicitytypes: EthnicityType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personEthnicityService: PersonEthnicityService,
        private ethnicityTypeService: EthnicityTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.ethnicityTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.ethnicitytypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personEthnicity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personEthnicityService.update(this.personEthnicity));
        } else {
            this.subscribeToSaveResponse(
                this.personEthnicityService.create(this.personEthnicity));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonEthnicity>) {
        result.subscribe((res: PersonEthnicity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonEthnicity) {
        this.eventManager.broadcast({ name: 'personEthnicityListModification', content: 'OK'});
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

    trackEthnicityTypeById(index: number, item: EthnicityType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-person-ethnicity-popup',
    template: ''
})
export class PersonEthnicityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personEthnicityPopupService: PersonEthnicityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personEthnicityPopupService
                    .open(PersonEthnicityDialogComponent, params['id']);
            } else {
                this.modalRef = this.personEthnicityPopupService
                    .open(PersonEthnicityDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
