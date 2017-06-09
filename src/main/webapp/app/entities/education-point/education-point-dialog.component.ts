import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { EducationPoint } from './education-point.model';
import { EducationPointPopupService } from './education-point-popup.service';
import { EducationPointService } from './education-point.service';
import { EducationLevelType, EducationLevelTypeService } from '../education-level-type';
import { Address, AddressService } from '../address';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-education-point-dialog',
    templateUrl: './education-point-dialog.component.html'
})
export class EducationPointDialogComponent implements OnInit {

    educationPoint: EducationPoint;
    authorities: any[];
    isSaving: boolean;

    educationleveltypes: EducationLevelType[];

    addresses: Address[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private educationPointService: EducationPointService,
        private educationLevelTypeService: EducationLevelTypeService,
        private addressService: AddressService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.educationLevelTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.educationleveltypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.addressService.query()
            .subscribe((res: ResponseWrapper) => { this.addresses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.educationPoint.id !== undefined) {
            this.subscribeToSaveResponse(
                this.educationPointService.update(this.educationPoint));
        } else {
            this.subscribeToSaveResponse(
                this.educationPointService.create(this.educationPoint));
        }
    }

    private subscribeToSaveResponse(result: Observable<EducationPoint>) {
        result.subscribe((res: EducationPoint) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EducationPoint) {
        this.eventManager.broadcast({ name: 'educationPointListModification', content: 'OK'});
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

    trackEducationLevelTypeById(index: number, item: EducationLevelType) {
        return item.id;
    }

    trackAddressById(index: number, item: Address) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-education-point-popup',
    template: ''
})
export class EducationPointPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private educationPointPopupService: EducationPointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.educationPointPopupService
                    .open(EducationPointDialogComponent, params['id']);
            } else {
                this.modalRef = this.educationPointPopupService
                    .open(EducationPointDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
