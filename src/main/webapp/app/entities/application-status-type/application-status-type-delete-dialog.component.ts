import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ApplicationStatusType } from './application-status-type.model';
import { ApplicationStatusTypePopupService } from './application-status-type-popup.service';
import { ApplicationStatusTypeService } from './application-status-type.service';

@Component({
    selector: 'jhi-application-status-type-delete-dialog',
    templateUrl: './application-status-type-delete-dialog.component.html'
})
export class ApplicationStatusTypeDeleteDialogComponent {

    applicationStatusType: ApplicationStatusType;

    constructor(
        private applicationStatusTypeService: ApplicationStatusTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.applicationStatusTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'applicationStatusTypeListModification',
                content: 'Deleted an applicationStatusType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-application-status-type-delete-popup',
    template: ''
})
export class ApplicationStatusTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private applicationStatusTypePopupService: ApplicationStatusTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.applicationStatusTypePopupService
                .open(ApplicationStatusTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
