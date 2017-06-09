import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Employment } from './employment.model';
import { EmploymentPopupService } from './employment-popup.service';
import { EmploymentService } from './employment.service';
import { Employer, EmployerService } from '../employer';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employment-dialog',
    templateUrl: './employment-dialog.component.html'
})
export class EmploymentDialogComponent implements OnInit {

    employment: Employment;
    authorities: any[];
    isSaving: boolean;

    employers: Employer[];

    people: Person[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private employmentService: EmploymentService,
        private employerService: EmployerService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.employerService.query()
            .subscribe((res: ResponseWrapper) => { this.employers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.employment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employmentService.update(this.employment));
        } else {
            this.subscribeToSaveResponse(
                this.employmentService.create(this.employment));
        }
    }

    private subscribeToSaveResponse(result: Observable<Employment>) {
        result.subscribe((res: Employment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Employment) {
        this.eventManager.broadcast({ name: 'employmentListModification', content: 'OK'});
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

    trackEmployerById(index: number, item: Employer) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-employment-popup',
    template: ''
})
export class EmploymentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employmentPopupService: EmploymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.employmentPopupService
                    .open(EmploymentDialogComponent, params['id']);
            } else {
                this.modalRef = this.employmentPopupService
                    .open(EmploymentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
