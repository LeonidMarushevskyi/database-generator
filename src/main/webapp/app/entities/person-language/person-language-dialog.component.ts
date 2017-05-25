import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PersonLanguage } from './person-language.model';
import { PersonLanguagePopupService } from './person-language-popup.service';
import { PersonLanguageService } from './person-language.service';
import { Person, PersonService } from '../person';
import { LanguageType, LanguageTypeService } from '../language-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-language-dialog',
    templateUrl: './person-language-dialog.component.html'
})
export class PersonLanguageDialogComponent implements OnInit {

    personLanguage: PersonLanguage;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    languagetypes: LanguageType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personLanguageService: PersonLanguageService,
        private personService: PersonService,
        private languageTypeService: LanguageTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.languageTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.languagetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personLanguage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personLanguageService.update(this.personLanguage));
        } else {
            this.subscribeToSaveResponse(
                this.personLanguageService.create(this.personLanguage));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonLanguage>) {
        result.subscribe((res: PersonLanguage) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonLanguage) {
        this.eventManager.broadcast({ name: 'personLanguageListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }

    trackLanguageTypeById(index: number, item: LanguageType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-person-language-popup',
    template: ''
})
export class PersonLanguagePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personLanguagePopupService: PersonLanguagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personLanguagePopupService
                    .open(PersonLanguageDialogComponent, params['id']);
            } else {
                this.modalRef = this.personLanguagePopupService
                    .open(PersonLanguageDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
