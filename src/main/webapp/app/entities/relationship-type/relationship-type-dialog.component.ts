import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { RelationshipType } from './relationship-type.model';
import { RelationshipTypePopupService } from './relationship-type-popup.service';
import { RelationshipTypeService } from './relationship-type.service';

@Component({
    selector: 'jhi-relationship-type-dialog',
    templateUrl: './relationship-type-dialog.component.html'
})
export class RelationshipTypeDialogComponent implements OnInit {

    relationshipType: RelationshipType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private relationshipTypeService: RelationshipTypeService,
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
        if (this.relationshipType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.relationshipTypeService.update(this.relationshipType));
        } else {
            this.subscribeToSaveResponse(
                this.relationshipTypeService.create(this.relationshipType));
        }
    }

    private subscribeToSaveResponse(result: Observable<RelationshipType>) {
        result.subscribe((res: RelationshipType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RelationshipType) {
        this.eventManager.broadcast({ name: 'relationshipTypeListModification', content: 'OK'});
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
    selector: 'jhi-relationship-type-popup',
    template: ''
})
export class RelationshipTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private relationshipTypePopupService: RelationshipTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.relationshipTypePopupService
                    .open(RelationshipTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.relationshipTypePopupService
                    .open(RelationshipTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
