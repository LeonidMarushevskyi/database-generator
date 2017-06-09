import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { RelationshipEventType } from './relationship-event-type.model';
import { RelationshipEventTypePopupService } from './relationship-event-type-popup.service';
import { RelationshipEventTypeService } from './relationship-event-type.service';

@Component({
    selector: 'jhi-relationship-event-type-dialog',
    templateUrl: './relationship-event-type-dialog.component.html'
})
export class RelationshipEventTypeDialogComponent implements OnInit {

    relationshipEventType: RelationshipEventType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private relationshipEventTypeService: RelationshipEventTypeService,
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
        if (this.relationshipEventType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.relationshipEventTypeService.update(this.relationshipEventType));
        } else {
            this.subscribeToSaveResponse(
                this.relationshipEventTypeService.create(this.relationshipEventType));
        }
    }

    private subscribeToSaveResponse(result: Observable<RelationshipEventType>) {
        result.subscribe((res: RelationshipEventType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RelationshipEventType) {
        this.eventManager.broadcast({ name: 'relationshipEventTypeListModification', content: 'OK'});
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
    selector: 'jhi-relationship-event-type-popup',
    template: ''
})
export class RelationshipEventTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private relationshipEventTypePopupService: RelationshipEventTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.relationshipEventTypePopupService
                    .open(RelationshipEventTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.relationshipEventTypePopupService
                    .open(RelationshipEventTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
