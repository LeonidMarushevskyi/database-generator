import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Household } from './household.model';
import { HouseholdPopupService } from './household-popup.service';
import { HouseholdService } from './household.service';
import { LanguageType, LanguageTypeService } from '../language-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-household-dialog',
    templateUrl: './household-dialog.component.html'
})
export class HouseholdDialogComponent implements OnInit {

    household: Household;
    authorities: any[];
    isSaving: boolean;

    languagetypes: LanguageType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private householdService: HouseholdService,
        private languageTypeService: LanguageTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.languageTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.languagetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.household.id !== undefined) {
            this.subscribeToSaveResponse(
                this.householdService.update(this.household));
        } else {
            this.subscribeToSaveResponse(
                this.householdService.create(this.household));
        }
    }

    private subscribeToSaveResponse(result: Observable<Household>) {
        result.subscribe((res: Household) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Household) {
        this.eventManager.broadcast({ name: 'householdListModification', content: 'OK'});
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

    trackLanguageTypeById(index: number, item: LanguageType) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-household-popup',
    template: ''
})
export class HouseholdPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private householdPopupService: HouseholdPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.householdPopupService
                    .open(HouseholdDialogComponent, params['id']);
            } else {
                this.modalRef = this.householdPopupService
                    .open(HouseholdDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
