import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { EmailAddress } from './email-address.model';
import { EmailAddressPopupService } from './email-address-popup.service';
import { EmailAddressService } from './email-address.service';

@Component({
    selector: 'jhi-email-address-delete-dialog',
    templateUrl: './email-address-delete-dialog.component.html'
})
export class EmailAddressDeleteDialogComponent {

    emailAddress: EmailAddress;

    constructor(
        private emailAddressService: EmailAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailAddressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'emailAddressListModification',
                content: 'Deleted an emailAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-address-delete-popup',
    template: ''
})
export class EmailAddressDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailAddressPopupService: EmailAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.emailAddressPopupService
                .open(EmailAddressDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
