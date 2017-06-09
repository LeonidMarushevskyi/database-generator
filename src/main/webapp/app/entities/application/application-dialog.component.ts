import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Application } from './application.model';
import { ApplicationPopupService } from './application-popup.service';
import { ApplicationService } from './application.service';
import { LicensureHistory, LicensureHistoryService } from '../licensure-history';
import { ChildPreferences, ChildPreferencesService } from '../child-preferences';
import { CountyType, CountyTypeService } from '../county-type';
import { ApplicationStatusType, ApplicationStatusTypeService } from '../application-status-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-application-dialog',
    templateUrl: './application-dialog.component.html'
})
export class ApplicationDialogComponent implements OnInit {

    application: Application;
    authorities: any[];
    isSaving: boolean;

    licensurehistories: LicensureHistory[];

    countytypes: CountyType[];

    applicationstatustypes: ApplicationStatusType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private applicationService: ApplicationService,
        private licensureHistoryService: LicensureHistoryService,
        private childPreferencesService: ChildPreferencesService,
        private countyTypeService: CountyTypeService,
        private applicationStatusTypeService: ApplicationStatusTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.licensureHistoryService
            .query({filter: 'application-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.application.licensureHistory || !this.application.licensureHistory.id) {
                    this.licensurehistories = res.json;
                } else {
                    this.licensureHistoryService
                        .find(this.application.licensureHistory.id)
                        .subscribe((subRes: LicensureHistory) => {
                            this.licensurehistories = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.countyTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.countytypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.applicationStatusTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.applicationstatustypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.application.id !== undefined) {
            this.subscribeToSaveResponse(
                this.applicationService.update(this.application));
        } else {
            this.subscribeToSaveResponse(
                this.applicationService.create(this.application));
        }
    }

    private subscribeToSaveResponse(result: Observable<Application>) {
        result.subscribe((res: Application) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Application) {
        this.eventManager.broadcast({ name: 'applicationListModification', content: 'OK'});
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

    trackLicensureHistoryById(index: number, item: LicensureHistory) {
        return item.id;
    }

    trackChildPreferencesById(index: number, item: ChildPreferences) {
        return item.id;
    }

    trackCountyTypeById(index: number, item: CountyType) {
        return item.id;
    }

    trackApplicationStatusTypeById(index: number, item: ApplicationStatusType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-application-popup',
    template: ''
})
export class ApplicationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private applicationPopupService: ApplicationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.applicationPopupService
                    .open(ApplicationDialogComponent, params['id']);
            } else {
                this.modalRef = this.applicationPopupService
                    .open(ApplicationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
