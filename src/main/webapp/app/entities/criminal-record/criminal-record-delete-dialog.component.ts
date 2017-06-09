import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CriminalRecord } from './criminal-record.model';
import { CriminalRecordPopupService } from './criminal-record-popup.service';
import { CriminalRecordService } from './criminal-record.service';

@Component({
    selector: 'jhi-criminal-record-delete-dialog',
    templateUrl: './criminal-record-delete-dialog.component.html'
})
export class CriminalRecordDeleteDialogComponent {

    criminalRecord: CriminalRecord;

    constructor(
        private criminalRecordService: CriminalRecordService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.criminalRecordService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'criminalRecordListModification',
                content: 'Deleted an criminalRecord'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-criminal-record-delete-popup',
    template: ''
})
export class CriminalRecordDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private criminalRecordPopupService: CriminalRecordPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.criminalRecordPopupService
                .open(CriminalRecordDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
