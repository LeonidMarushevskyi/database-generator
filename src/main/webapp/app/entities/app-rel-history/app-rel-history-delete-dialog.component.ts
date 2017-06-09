import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AppRelHistory } from './app-rel-history.model';
import { AppRelHistoryPopupService } from './app-rel-history-popup.service';
import { AppRelHistoryService } from './app-rel-history.service';

@Component({
    selector: 'jhi-app-rel-history-delete-dialog',
    templateUrl: './app-rel-history-delete-dialog.component.html'
})
export class AppRelHistoryDeleteDialogComponent {

    appRelHistory: AppRelHistory;

    constructor(
        private appRelHistoryService: AppRelHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appRelHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'appRelHistoryListModification',
                content: 'Deleted an appRelHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-app-rel-history-delete-popup',
    template: ''
})
export class AppRelHistoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appRelHistoryPopupService: AppRelHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.appRelHistoryPopupService
                .open(AppRelHistoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
