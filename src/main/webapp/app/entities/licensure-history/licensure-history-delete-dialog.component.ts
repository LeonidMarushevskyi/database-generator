import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { LicensureHistory } from './licensure-history.model';
import { LicensureHistoryPopupService } from './licensure-history-popup.service';
import { LicensureHistoryService } from './licensure-history.service';

@Component({
    selector: 'jhi-licensure-history-delete-dialog',
    templateUrl: './licensure-history-delete-dialog.component.html'
})
export class LicensureHistoryDeleteDialogComponent {

    licensureHistory: LicensureHistory;

    constructor(
        private licensureHistoryService: LicensureHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.licensureHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'licensureHistoryListModification',
                content: 'Deleted an licensureHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-licensure-history-delete-popup',
    template: ''
})
export class LicensureHistoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private licensureHistoryPopupService: LicensureHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.licensureHistoryPopupService
                .open(LicensureHistoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
