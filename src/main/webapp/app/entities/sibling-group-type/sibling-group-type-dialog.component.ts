import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { SiblingGroupType } from './sibling-group-type.model';
import { SiblingGroupTypePopupService } from './sibling-group-type-popup.service';
import { SiblingGroupTypeService } from './sibling-group-type.service';

@Component({
    selector: 'jhi-sibling-group-type-dialog',
    templateUrl: './sibling-group-type-dialog.component.html'
})
export class SiblingGroupTypeDialogComponent implements OnInit {

    siblingGroupType: SiblingGroupType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private siblingGroupTypeService: SiblingGroupTypeService,
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
        if (this.siblingGroupType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.siblingGroupTypeService.update(this.siblingGroupType));
        } else {
            this.subscribeToSaveResponse(
                this.siblingGroupTypeService.create(this.siblingGroupType));
        }
    }

    private subscribeToSaveResponse(result: Observable<SiblingGroupType>) {
        result.subscribe((res: SiblingGroupType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SiblingGroupType) {
        this.eventManager.broadcast({ name: 'siblingGroupTypeListModification', content: 'OK'});
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
    selector: 'jhi-sibling-group-type-popup',
    template: ''
})
export class SiblingGroupTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private siblingGroupTypePopupService: SiblingGroupTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.siblingGroupTypePopupService
                    .open(SiblingGroupTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.siblingGroupTypePopupService
                    .open(SiblingGroupTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
