import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FacilityAddress } from './facility-address.model';
import { FacilityAddressPopupService } from './facility-address-popup.service';
import { FacilityAddressService } from './facility-address.service';

@Component({
    selector: 'jhi-facility-address-delete-dialog',
    templateUrl: './facility-address-delete-dialog.component.html'
})
export class FacilityAddressDeleteDialogComponent {

    facilityAddress: FacilityAddress;

    constructor(
        private facilityAddressService: FacilityAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facilityAddressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'facilityAddressListModification',
                content: 'Deleted an facilityAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-facility-address-delete-popup',
    template: ''
})
export class FacilityAddressDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facilityAddressPopupService: FacilityAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.facilityAddressPopupService
                .open(FacilityAddressDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
