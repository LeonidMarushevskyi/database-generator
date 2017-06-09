import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ApplicationStatusType } from './application-status-type.model';
import { ApplicationStatusTypePopupService } from './application-status-type-popup.service';
import { ApplicationStatusTypeService } from './application-status-type.service';

@Component({
    selector: 'jhi-application-status-type-dialog',
    templateUrl: './application-status-type-dialog.component.html'
})
export class ApplicationStatusTypeDialogComponent implements OnInit {

    applicationStatusType: ApplicationStatusType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private applicationStatusTypeService: ApplicationStatusTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.applicationStatusType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.applicationStatusTypeService.update(this.applicationStatusType));
        } else {
            this.subscribeToSaveResponse(
                this.applicationStatusTypeService.create(this.applicationStatusType));
        }
    }

    private subscribeToSaveResponse(result: Observable<ApplicationStatusType>) {
        result.subscribe((res: ApplicationStatusType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ApplicationStatusType) {
        this.eventManager.broadcast({ name: 'applicationStatusTypeListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-application-status-type-popup',
    template: ''
})
export class ApplicationStatusTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private applicationStatusTypePopupService: ApplicationStatusTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.applicationStatusTypePopupService
                    .open(ApplicationStatusTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.applicationStatusTypePopupService
                    .open(ApplicationStatusTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
