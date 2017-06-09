import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PosessionType } from './posession-type.model';
import { PosessionTypePopupService } from './posession-type-popup.service';
import { PosessionTypeService } from './posession-type.service';

@Component({
    selector: 'jhi-posession-type-dialog',
    templateUrl: './posession-type-dialog.component.html'
})
export class PosessionTypeDialogComponent implements OnInit {

    posessionType: PosessionType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private posessionTypeService: PosessionTypeService,
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
        if (this.posessionType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.posessionTypeService.update(this.posessionType));
        } else {
            this.subscribeToSaveResponse(
                this.posessionTypeService.create(this.posessionType));
        }
    }

    private subscribeToSaveResponse(result: Observable<PosessionType>) {
        result.subscribe((res: PosessionType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PosessionType) {
        this.eventManager.broadcast({ name: 'posessionTypeListModification', content: 'OK'});
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
    selector: 'jhi-posession-type-popup',
    template: ''
})
export class PosessionTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private posessionTypePopupService: PosessionTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.posessionTypePopupService
                    .open(PosessionTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.posessionTypePopupService
                    .open(PosessionTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
