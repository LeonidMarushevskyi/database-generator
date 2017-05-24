import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { EthnicityType } from './ethnicity-type.model';
import { EthnicityTypePopupService } from './ethnicity-type-popup.service';
import { EthnicityTypeService } from './ethnicity-type.service';

@Component({
    selector: 'jhi-ethnicity-type-delete-dialog',
    templateUrl: './ethnicity-type-delete-dialog.component.html'
})
export class EthnicityTypeDeleteDialogComponent {

    ethnicityType: EthnicityType;

    constructor(
        private ethnicityTypeService: EthnicityTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.ethnicityTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ethnicityTypeListModification',
                content: 'Deleted an ethnicityType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ethnicity-type-delete-popup',
    template: ''
})
export class EthnicityTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private ethnicityTypePopupService: EthnicityTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.ethnicityTypePopupService
                .open(EthnicityTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
