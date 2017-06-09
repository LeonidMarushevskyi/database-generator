import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { GenderType } from './gender-type.model';
import { GenderTypePopupService } from './gender-type-popup.service';
import { GenderTypeService } from './gender-type.service';

@Component({
    selector: 'jhi-gender-type-dialog',
    templateUrl: './gender-type-dialog.component.html'
})
export class GenderTypeDialogComponent implements OnInit {

    genderType: GenderType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private genderTypeService: GenderTypeService,
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
        if (this.genderType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.genderTypeService.update(this.genderType));
        } else {
            this.subscribeToSaveResponse(
                this.genderTypeService.create(this.genderType));
        }
    }

    private subscribeToSaveResponse(result: Observable<GenderType>) {
        result.subscribe((res: GenderType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: GenderType) {
        this.eventManager.broadcast({ name: 'genderTypeListModification', content: 'OK'});
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
    selector: 'jhi-gender-type-popup',
    template: ''
})
export class GenderTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private genderTypePopupService: GenderTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.genderTypePopupService
                    .open(GenderTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.genderTypePopupService
                    .open(GenderTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
