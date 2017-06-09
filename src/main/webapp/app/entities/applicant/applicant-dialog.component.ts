import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Applicant } from './applicant.model';
import { ApplicantPopupService } from './applicant-popup.service';
import { ApplicantService } from './applicant.service';
import { HouseholdAdult, HouseholdAdultService } from '../household-adult';
import { Application, ApplicationService } from '../application';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-applicant-dialog',
    templateUrl: './applicant-dialog.component.html'
})
export class ApplicantDialogComponent implements OnInit {

    applicant: Applicant;
    authorities: any[];
    isSaving: boolean;

    householdpeople: HouseholdAdult[];

    applications: Application[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private applicantService: ApplicantService,
        private householdAdultService: HouseholdAdultService,
        private applicationService: ApplicationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.householdAdultService
            .query({filter: 'applicant-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.applicant.householdPerson || !this.applicant.householdPerson.id) {
                    this.householdpeople = res.json;
                } else {
                    this.householdAdultService
                        .find(this.applicant.householdPerson.id)
                        .subscribe((subRes: HouseholdAdult) => {
                            this.householdpeople = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.applicationService.query()
            .subscribe((res: ResponseWrapper) => { this.applications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.applicant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.applicantService.update(this.applicant));
        } else {
            this.subscribeToSaveResponse(
                this.applicantService.create(this.applicant));
        }
    }

    private subscribeToSaveResponse(result: Observable<Applicant>) {
        result.subscribe((res: Applicant) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Applicant) {
        this.eventManager.broadcast({ name: 'applicantListModification', content: 'OK'});
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

    trackHouseholdAdultById(index: number, item: HouseholdAdult) {
        return item.id;
    }

    trackApplicationById(index: number, item: Application) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-applicant-popup',
    template: ''
})
export class ApplicantPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private applicantPopupService: ApplicantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.applicantPopupService
                    .open(ApplicantDialogComponent, params['id']);
            } else {
                this.modalRef = this.applicantPopupService
                    .open(ApplicantDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
