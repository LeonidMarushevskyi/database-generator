import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Person } from './person.model';
import { PersonPopupService } from './person-popup.service';
import { PersonService } from './person.service';
import { PersonEthnicity, PersonEthnicityService } from '../person-ethnicity';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-dialog',
    templateUrl: './person-dialog.component.html'
})
export class PersonDialogComponent implements OnInit {

    person: Person;
    authorities: any[];
    isSaving: boolean;

    ethnicities: PersonEthnicity[];
    dateOfBirthDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personService: PersonService,
        private personEthnicityService: PersonEthnicityService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personEthnicityService
            .query({filter: 'person-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.person.ethnicityId) {
                    this.ethnicities = res.json;
                } else {
                    this.personEthnicityService
                        .find(this.person.ethnicityId)
                        .subscribe((subRes: PersonEthnicity) => {
                            this.ethnicities = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.person.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personService.update(this.person));
        } else {
            this.subscribeToSaveResponse(
                this.personService.create(this.person));
        }
    }

    private subscribeToSaveResponse(result: Observable<Person>) {
        result.subscribe((res: Person) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Person) {
        this.eventManager.broadcast({ name: 'personListModification', content: 'OK'});
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

    trackPersonEthnicityById(index: number, item: PersonEthnicity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-person-popup',
    template: ''
})
export class PersonPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personPopupService: PersonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personPopupService
                    .open(PersonDialogComponent, params['id']);
            } else {
                this.modalRef = this.personPopupService
                    .open(PersonDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
