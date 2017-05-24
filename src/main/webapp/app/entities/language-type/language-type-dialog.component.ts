import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { LanguageType } from './language-type.model';
import { LanguageTypePopupService } from './language-type-popup.service';
import { LanguageTypeService } from './language-type.service';

@Component({
    selector: 'jhi-language-type-dialog',
    templateUrl: './language-type-dialog.component.html'
})
export class LanguageTypeDialogComponent implements OnInit {

    languageType: LanguageType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private languageTypeService: LanguageTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.languageType.id !== undefined) {
            this.languageTypeService.update(this.languageType)
                .subscribe((res: LanguageType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.languageTypeService.create(this.languageType)
                .subscribe((res: LanguageType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: LanguageType) {
        this.eventManager.broadcast({ name: 'languageTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-language-type-popup',
    template: ''
})
export class LanguageTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private languageTypePopupService: LanguageTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.languageTypePopupService
                    .open(LanguageTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.languageTypePopupService
                    .open(LanguageTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
