import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PhoneNumberType } from './phone-number-type.model';
import { PhoneNumberTypePopupService } from './phone-number-type-popup.service';
import { PhoneNumberTypeService } from './phone-number-type.service';

@Component({
    selector: 'jhi-phone-number-type-delete-dialog',
    templateUrl: './phone-number-type-delete-dialog.component.html'
})
export class PhoneNumberTypeDeleteDialogComponent {

    phoneNumberType: PhoneNumberType;

    constructor(
        private phoneNumberTypeService: PhoneNumberTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.phoneNumberTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'phoneNumberTypeListModification',
                content: 'Deleted an phoneNumberType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-phone-number-type-delete-popup',
    template: ''
})
export class PhoneNumberTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private phoneNumberTypePopupService: PhoneNumberTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.phoneNumberTypePopupService
                .open(PhoneNumberTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
