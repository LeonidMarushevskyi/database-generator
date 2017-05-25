import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AddressType } from './address-type.model';
import { AddressTypePopupService } from './address-type-popup.service';
import { AddressTypeService } from './address-type.service';

@Component({
    selector: 'jhi-address-type-delete-dialog',
    templateUrl: './address-type-delete-dialog.component.html'
})
export class AddressTypeDeleteDialogComponent {

    addressType: AddressType;

    constructor(
        private addressTypeService: AddressTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.addressTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'addressTypeListModification',
                content: 'Deleted an addressType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-address-type-delete-popup',
    template: ''
})
export class AddressTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressTypePopupService: AddressTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.addressTypePopupService
                .open(AddressTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
