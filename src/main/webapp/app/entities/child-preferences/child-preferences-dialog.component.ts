import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ChildPreferences } from './child-preferences.model';
import { ChildPreferencesPopupService } from './child-preferences-popup.service';
import { ChildPreferencesService } from './child-preferences.service';
import { AgeGroupType, AgeGroupTypeService } from '../age-group-type';
import { SiblingGroupType, SiblingGroupTypeService } from '../sibling-group-type';
import { Application, ApplicationService } from '../application';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-child-preferences-dialog',
    templateUrl: './child-preferences-dialog.component.html'
})
export class ChildPreferencesDialogComponent implements OnInit {

    childPreferences: ChildPreferences;
    authorities: any[];
    isSaving: boolean;

    agegrouptypes: AgeGroupType[];

    siblinggrouptypes: SiblingGroupType[];

    applications: Application[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private childPreferencesService: ChildPreferencesService,
        private ageGroupTypeService: AgeGroupTypeService,
        private siblingGroupTypeService: SiblingGroupTypeService,
        private applicationService: ApplicationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.ageGroupTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.agegrouptypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.siblingGroupTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.siblinggrouptypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.applicationService.query()
            .subscribe((res: ResponseWrapper) => { this.applications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.childPreferences.id !== undefined) {
            this.subscribeToSaveResponse(
                this.childPreferencesService.update(this.childPreferences));
        } else {
            this.subscribeToSaveResponse(
                this.childPreferencesService.create(this.childPreferences));
        }
    }

    private subscribeToSaveResponse(result: Observable<ChildPreferences>) {
        result.subscribe((res: ChildPreferences) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ChildPreferences) {
        this.eventManager.broadcast({ name: 'childPreferencesListModification', content: 'OK'});
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

    trackAgeGroupTypeById(index: number, item: AgeGroupType) {
        return item.id;
    }

    trackSiblingGroupTypeById(index: number, item: SiblingGroupType) {
        return item.id;
    }

    trackApplicationById(index: number, item: Application) {
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
    selector: 'jhi-child-preferences-popup',
    template: ''
})
export class ChildPreferencesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private childPreferencesPopupService: ChildPreferencesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.childPreferencesPopupService
                    .open(ChildPreferencesDialogComponent, params['id']);
            } else {
                this.modalRef = this.childPreferencesPopupService
                    .open(ChildPreferencesDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
