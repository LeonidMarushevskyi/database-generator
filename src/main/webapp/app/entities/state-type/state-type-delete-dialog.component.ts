import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { StateType } from './state-type.model';
import { StateTypePopupService } from './state-type-popup.service';
import { StateTypeService } from './state-type.service';

@Component({
    selector: 'jhi-state-type-delete-dialog',
    templateUrl: './state-type-delete-dialog.component.html'
})
export class StateTypeDeleteDialogComponent {

    stateType: StateType;

    constructor(
        private stateTypeService: StateTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stateTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'stateTypeListModification',
                content: 'Deleted an stateType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-state-type-delete-popup',
    template: ''
})
export class StateTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stateTypePopupService: StateTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.stateTypePopupService
                .open(StateTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
