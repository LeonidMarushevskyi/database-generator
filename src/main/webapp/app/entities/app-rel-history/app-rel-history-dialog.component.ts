import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AppRelHistory } from './app-rel-history.model';
import { AppRelHistoryPopupService } from './app-rel-history-popup.service';
import { AppRelHistoryService } from './app-rel-history.service';
import { RelationshipEvent, RelationshipEventService } from '../relationship-event';
import { RelationshipType, RelationshipTypeService } from '../relationship-type';
import { Applicant, ApplicantService } from '../applicant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-app-rel-history-dialog',
    templateUrl: './app-rel-history-dialog.component.html'
})
export class AppRelHistoryDialogComponent implements OnInit {

    appRelHistory: AppRelHistory;
    authorities: any[];
    isSaving: boolean;

    startevents: RelationshipEvent[];

    endevents: RelationshipEvent[];

    relationshiptypes: RelationshipType[];

    applicants: Applicant[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private appRelHistoryService: AppRelHistoryService,
        private relationshipEventService: RelationshipEventService,
        private relationshipTypeService: RelationshipTypeService,
        private applicantService: ApplicantService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.relationshipEventService
            .query({filter: 'relationship-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.appRelHistory.startEvent || !this.appRelHistory.startEvent.id) {
                    this.startevents = res.json;
                } else {
                    this.relationshipEventService
                        .find(this.appRelHistory.startEvent.id)
                        .subscribe((subRes: RelationshipEvent) => {
                            this.startevents = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.relationshipEventService
            .query({filter: 'relationship-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.appRelHistory.endEvent || !this.appRelHistory.endEvent.id) {
                    this.endevents = res.json;
                } else {
                    this.relationshipEventService
                        .find(this.appRelHistory.endEvent.id)
                        .subscribe((subRes: RelationshipEvent) => {
                            this.endevents = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.relationshipTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.relationshiptypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.applicantService.query()
            .subscribe((res: ResponseWrapper) => { this.applicants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.appRelHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.appRelHistoryService.update(this.appRelHistory));
        } else {
            this.subscribeToSaveResponse(
                this.appRelHistoryService.create(this.appRelHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<AppRelHistory>) {
        result.subscribe((res: AppRelHistory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AppRelHistory) {
        this.eventManager.broadcast({ name: 'appRelHistoryListModification', content: 'OK'});
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

    trackRelationshipEventById(index: number, item: RelationshipEvent) {
        return item.id;
    }

    trackRelationshipTypeById(index: number, item: RelationshipType) {
        return item.id;
    }

    trackApplicantById(index: number, item: Applicant) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-app-rel-history-popup',
    template: ''
})
export class AppRelHistoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appRelHistoryPopupService: AppRelHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.appRelHistoryPopupService
                    .open(AppRelHistoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.appRelHistoryPopupService
                    .open(AppRelHistoryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
