import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { LicensureHistory } from './licensure-history.model';
import { LicensureHistoryPopupService } from './licensure-history-popup.service';
import { LicensureHistoryService } from './licensure-history.service';
import { Application, ApplicationService } from '../application';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-licensure-history-dialog',
    templateUrl: './licensure-history-dialog.component.html'
})
export class LicensureHistoryDialogComponent implements OnInit {

    licensureHistory: LicensureHistory;
    authorities: any[];
    isSaving: boolean;

    applications: Application[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private licensureHistoryService: LicensureHistoryService,
        private applicationService: ApplicationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.applicationService.query()
            .subscribe((res: ResponseWrapper) => { this.applications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.licensureHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.licensureHistoryService.update(this.licensureHistory));
        } else {
            this.subscribeToSaveResponse(
                this.licensureHistoryService.create(this.licensureHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<LicensureHistory>) {
        result.subscribe((res: LicensureHistory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: LicensureHistory) {
        this.eventManager.broadcast({ name: 'licensureHistoryListModification', content: 'OK'});
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

    trackApplicationById(index: number, item: Application) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-licensure-history-popup',
    template: ''
})
export class LicensureHistoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private licensureHistoryPopupService: LicensureHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.licensureHistoryPopupService
                    .open(LicensureHistoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.licensureHistoryPopupService
                    .open(LicensureHistoryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
