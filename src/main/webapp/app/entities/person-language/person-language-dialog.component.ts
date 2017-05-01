import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PersonLanguage } from './person-language.model';
import { PersonLanguagePopupService } from './person-language-popup.service';
import { PersonLanguageService } from './person-language.service';
import { Person, PersonService } from '../person';
import { LanguageType, LanguageTypeService } from '../language-type';

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
        this.personService.query().subscribe(
            (res: Response) => { this.people = res.json(); }, (res: Response) => this.onError(res.json()));
        this.languageTypeService.query().subscribe(
            (res: Response) => { this.languagetypes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.personLanguage.id !== undefined) {
            this.personLanguageService.update(this.personLanguage)
                .subscribe((res: PersonLanguage) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.personLanguageService.create(this.personLanguage)
                .subscribe((res: PersonLanguage) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: PersonLanguage) {
        this.eventManager.broadcast({ name: 'personLanguageListModification', content: 'OK'});
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

    constructor (
        private route: ActivatedRoute,
        private personLanguagePopupService: PersonLanguagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
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
