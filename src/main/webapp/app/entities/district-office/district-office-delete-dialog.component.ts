import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { DistrictOffice } from './district-office.model';
import { DistrictOfficePopupService } from './district-office-popup.service';
import { DistrictOfficeService } from './district-office.service';

@Component({
    selector: 'jhi-district-office-delete-dialog',
    templateUrl: './district-office-delete-dialog.component.html'
})
export class DistrictOfficeDeleteDialogComponent {

    districtOffice: DistrictOffice;

    constructor(
        private districtOfficeService: DistrictOfficeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.districtOfficeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'districtOfficeListModification',
                content: 'Deleted an districtOffice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-district-office-delete-popup',
    template: ''
})
export class DistrictOfficeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private districtOfficePopupService: DistrictOfficePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.districtOfficePopupService
                .open(DistrictOfficeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
