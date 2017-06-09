import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { RelationshipEvent } from './relationship-event.model';
import { RelationshipEventPopupService } from './relationship-event-popup.service';
import { RelationshipEventService } from './relationship-event.service';
import { RelationshipEventType, RelationshipEventTypeService } from '../relationship-event-type';
import { AppRelHistory, AppRelHistoryService } from '../app-rel-history';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-relationship-event-dialog',
    templateUrl: './relationship-event-dialog.component.html'
})
export class RelationshipEventDialogComponent implements OnInit {

    relationshipEvent: RelationshipEvent;
    authorities: any[];
    isSaving: boolean;

    relationshipeventtypes: RelationshipEventType[];

    apprelhistories: AppRelHistory[];
    eventDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private relationshipEventService: RelationshipEventService,
        private relationshipEventTypeService: RelationshipEventTypeService,
        private appRelHistoryService: AppRelHistoryService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.relationshipEventTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.relationshipeventtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.appRelHistoryService.query()
            .subscribe((res: ResponseWrapper) => { this.apprelhistories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.relationshipEvent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.relationshipEventService.update(this.relationshipEvent));
        } else {
            this.subscribeToSaveResponse(
                this.relationshipEventService.create(this.relationshipEvent));
        }
    }

    private subscribeToSaveResponse(result: Observable<RelationshipEvent>) {
        result.subscribe((res: RelationshipEvent) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RelationshipEvent) {
        this.eventManager.broadcast({ name: 'relationshipEventListModification', content: 'OK'});
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

    trackRelationshipEventTypeById(index: number, item: RelationshipEventType) {
        return item.id;
    }

    trackAppRelHistoryById(index: number, item: AppRelHistory) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-relationship-event-popup',
    template: ''
})
export class RelationshipEventPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private relationshipEventPopupService: RelationshipEventPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.relationshipEventPopupService
                    .open(RelationshipEventDialogComponent, params['id']);
            } else {
                this.modalRef = this.relationshipEventPopupService
                    .open(RelationshipEventDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
