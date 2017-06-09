import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ChildPreferences } from './child-preferences.model';
import { ChildPreferencesPopupService } from './child-preferences-popup.service';
import { ChildPreferencesService } from './child-preferences.service';

@Component({
    selector: 'jhi-child-preferences-delete-dialog',
    templateUrl: './child-preferences-delete-dialog.component.html'
})
export class ChildPreferencesDeleteDialogComponent {

    childPreferences: ChildPreferences;

    constructor(
        private childPreferencesService: ChildPreferencesService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.childPreferencesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'childPreferencesListModification',
                content: 'Deleted an childPreferences'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-child-preferences-delete-popup',
    template: ''
})
export class ChildPreferencesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private childPreferencesPopupService: ChildPreferencesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.childPreferencesPopupService
                .open(ChildPreferencesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
