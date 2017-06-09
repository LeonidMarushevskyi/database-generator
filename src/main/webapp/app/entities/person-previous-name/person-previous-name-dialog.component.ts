import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PersonPreviousName } from './person-previous-name.model';
import { PersonPreviousNamePopupService } from './person-previous-name-popup.service';
import { PersonPreviousNameService } from './person-previous-name.service';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-previous-name-dialog',
    templateUrl: './person-previous-name-dialog.component.html'
})
export class PersonPreviousNameDialogComponent implements OnInit {

    personPreviousName: PersonPreviousName;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personPreviousNameService: PersonPreviousNameService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personPreviousName.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personPreviousNameService.update(this.personPreviousName));
        } else {
            this.subscribeToSaveResponse(
                this.personPreviousNameService.create(this.personPreviousName));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonPreviousName>) {
        result.subscribe((res: PersonPreviousName) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonPreviousName) {
        this.eventManager.broadcast({ name: 'personPreviousNameListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-person-previous-name-popup',
    template: ''
})
export class PersonPreviousNamePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personPreviousNamePopupService: PersonPreviousNamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personPreviousNamePopupService
                    .open(PersonPreviousNameDialogComponent, params['id']);
            } else {
                this.modalRef = this.personPreviousNamePopupService
                    .open(PersonPreviousNameDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
