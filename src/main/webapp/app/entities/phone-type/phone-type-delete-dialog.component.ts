import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PhoneType } from './phone-type.model';
import { PhoneTypePopupService } from './phone-type-popup.service';
import { PhoneTypeService } from './phone-type.service';

@Component({
    selector: 'jhi-phone-type-delete-dialog',
    templateUrl: './phone-type-delete-dialog.component.html'
})
export class PhoneTypeDeleteDialogComponent {

    phoneType: PhoneType;

    constructor(
        private phoneTypeService: PhoneTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.phoneTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'phoneTypeListModification',
                content: 'Deleted an phoneType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-phone-type-delete-popup',
    template: ''
})
export class PhoneTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private phoneTypePopupService: PhoneTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.phoneTypePopupService
                .open(PhoneTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
