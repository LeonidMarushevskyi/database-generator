import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DeterminedChild } from './determined-child.model';
import { DeterminedChildPopupService } from './determined-child-popup.service';
import { DeterminedChildService } from './determined-child.service';
import { Person, PersonService } from '../person';
import { CountyType, CountyTypeService } from '../county-type';
import { Application, ApplicationService } from '../application';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-determined-child-dialog',
    templateUrl: './determined-child-dialog.component.html'
})
export class DeterminedChildDialogComponent implements OnInit {

    determinedChild: DeterminedChild;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    countytypes: CountyType[];

    applications: Application[];
    dateOfPlacementDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private determinedChildService: DeterminedChildService,
        private personService: PersonService,
        private countyTypeService: CountyTypeService,
        private applicationService: ApplicationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService
            .query({filter: 'determinedchild-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.determinedChild.person || !this.determinedChild.person.id) {
                    this.people = res.json;
                } else {
                    this.personService
                        .find(this.determinedChild.person.id)
                        .subscribe((subRes: Person) => {
                            this.people = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.countyTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.countytypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.applicationService.query()
            .subscribe((res: ResponseWrapper) => { this.applications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.determinedChild.id !== undefined) {
            this.subscribeToSaveResponse(
                this.determinedChildService.update(this.determinedChild));
        } else {
            this.subscribeToSaveResponse(
                this.determinedChildService.create(this.determinedChild));
        }
    }

    private subscribeToSaveResponse(result: Observable<DeterminedChild>) {
        result.subscribe((res: DeterminedChild) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DeterminedChild) {
        this.eventManager.broadcast({ name: 'determinedChildListModification', content: 'OK'});
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

    trackCountyTypeById(index: number, item: CountyType) {
        return item.id;
    }

    trackApplicationById(index: number, item: Application) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-determined-child-popup',
    template: ''
})
export class DeterminedChildPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private determinedChildPopupService: DeterminedChildPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.determinedChildPopupService
                    .open(DeterminedChildDialogComponent, params['id']);
            } else {
                this.modalRef = this.determinedChildPopupService
                    .open(DeterminedChildDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
