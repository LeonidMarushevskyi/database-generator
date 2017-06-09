import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PosessionType } from './posession-type.model';
import { PosessionTypePopupService } from './posession-type-popup.service';
import { PosessionTypeService } from './posession-type.service';

@Component({
    selector: 'jhi-posession-type-delete-dialog',
    templateUrl: './posession-type-delete-dialog.component.html'
})
export class PosessionTypeDeleteDialogComponent {

    posessionType: PosessionType;

    constructor(
        private posessionTypeService: PosessionTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.posessionTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'posessionTypeListModification',
                content: 'Deleted an posessionType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-posession-type-delete-popup',
    template: ''
})
export class PosessionTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private posessionTypePopupService: PosessionTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.posessionTypePopupService
                .open(PosessionTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
