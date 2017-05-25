import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClearedPOC } from './cleared-poc.model';
import { ClearedPOCPopupService } from './cleared-poc-popup.service';
import { ClearedPOCService } from './cleared-poc.service';

@Component({
    selector: 'jhi-cleared-poc-delete-dialog',
    templateUrl: './cleared-poc-delete-dialog.component.html'
})
export class ClearedPOCDeleteDialogComponent {

    clearedPOC: ClearedPOC;

    constructor(
        private clearedPOCService: ClearedPOCService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clearedPOCService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clearedPOCListModification',
                content: 'Deleted an clearedPOC'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cleared-poc-delete-popup',
    template: ''
})
export class ClearedPOCDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clearedPOCPopupService: ClearedPOCPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clearedPOCPopupService
                .open(ClearedPOCDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
