import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PersonAddress } from './person-address.model';
import { PersonAddressPopupService } from './person-address-popup.service';
import { PersonAddressService } from './person-address.service';

@Component({
    selector: 'jhi-person-address-delete-dialog',
    templateUrl: './person-address-delete-dialog.component.html'
})
export class PersonAddressDeleteDialogComponent {

    personAddress: PersonAddress;

    constructor(
        private personAddressService: PersonAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.personAddressService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personAddressListModification',
                content: 'Deleted an personAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-person-address-delete-popup',
    template: ''
})
export class PersonAddressDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private personAddressPopupService: PersonAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.personAddressPopupService
                .open(PersonAddressDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
