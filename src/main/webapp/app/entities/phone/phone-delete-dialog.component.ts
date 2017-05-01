import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Phone } from './phone.model';
import { PhonePopupService } from './phone-popup.service';
import { PhoneService } from './phone.service';

@Component({
    selector: 'jhi-phone-delete-dialog',
    templateUrl: './phone-delete-dialog.component.html'
})
export class PhoneDeleteDialogComponent {

    phone: Phone;

    constructor(
        private phoneService: PhoneService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.phoneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'phoneListModification',
                content: 'Deleted an phone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-phone-delete-popup',
    template: ''
})
export class PhoneDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private phonePopupService: PhonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.phonePopupService
                .open(PhoneDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
