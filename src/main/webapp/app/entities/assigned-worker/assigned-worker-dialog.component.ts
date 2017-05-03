import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AssignedWorker } from './assigned-worker.model';
import { AssignedWorkerPopupService } from './assigned-worker-popup.service';
import { AssignedWorkerService } from './assigned-worker.service';
import { Person, PersonService } from '../person';

@Component({
    selector: 'jhi-assigned-worker-dialog',
    templateUrl: './assigned-worker-dialog.component.html'
})
export class AssignedWorkerDialogComponent implements OnInit {

    assignedWorker: AssignedWorker;
    authorities: any[];
    isSaving: boolean;

    people: Person[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private assignedWorkerService: AssignedWorkerService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query({filter: 'assignedworker-is-null'}).subscribe((res: Response) => {
            if (!this.assignedWorker.personId) {
                this.people = res.json();
            } else {
                this.personService.find(this.assignedWorker.personId).subscribe((subRes: Person) => {
                    this.people = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.assignedWorker.id !== undefined) {
            this.assignedWorkerService.update(this.assignedWorker)
                .subscribe((res: AssignedWorker) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.assignedWorkerService.create(this.assignedWorker)
                .subscribe((res: AssignedWorker) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: AssignedWorker) {
        this.eventManager.broadcast({ name: 'assignedWorkerListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-assigned-worker-popup',
    template: ''
})
export class AssignedWorkerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private assignedWorkerPopupService: AssignedWorkerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.assignedWorkerPopupService
                    .open(AssignedWorkerDialogComponent, params['id']);
            } else {
                this.modalRef = this.assignedWorkerPopupService
                    .open(AssignedWorkerDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
