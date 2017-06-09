import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { GenderType } from './gender-type.model';
import { GenderTypePopupService } from './gender-type-popup.service';
import { GenderTypeService } from './gender-type.service';

@Component({
    selector: 'jhi-gender-type-delete-dialog',
    templateUrl: './gender-type-delete-dialog.component.html'
})
export class GenderTypeDeleteDialogComponent {

    genderType: GenderType;

    constructor(
        private genderTypeService: GenderTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.genderTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'genderTypeListModification',
                content: 'Deleted an genderType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gender-type-delete-popup',
    template: ''
})
export class GenderTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private genderTypePopupService: GenderTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.genderTypePopupService
                .open(GenderTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
