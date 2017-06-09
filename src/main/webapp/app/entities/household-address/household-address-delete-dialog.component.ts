import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { HouseholdAddress } from './household-address.model';
import { HouseholdAddressPopupService } from './household-address-popup.service';
import { HouseholdAddressService } from './household-address.service';

@Component({
    selector: 'jhi-household-address-delete-dialog',
    templateUrl: './household-address-delete-dialog.component.html'
})
export class HouseholdAddressDeleteDialogComponent {

    householdAddress: HouseholdAddress;

    constructor(
        private householdAddressService: HouseholdAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.householdAddressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'householdAddressListModification',
                content: 'Deleted an householdAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-household-address-delete-popup',
    template: ''
})
export class HouseholdAddressDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private householdAddressPopupService: HouseholdAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.householdAddressPopupService
                .open(HouseholdAddressDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
