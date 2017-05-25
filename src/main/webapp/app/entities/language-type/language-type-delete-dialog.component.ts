import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { LanguageType } from './language-type.model';
import { LanguageTypePopupService } from './language-type-popup.service';
import { LanguageTypeService } from './language-type.service';

@Component({
    selector: 'jhi-language-type-delete-dialog',
    templateUrl: './language-type-delete-dialog.component.html'
})
export class LanguageTypeDeleteDialogComponent {

    languageType: LanguageType;

    constructor(
        private languageTypeService: LanguageTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.languageTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'languageTypeListModification',
                content: 'Deleted an languageType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-language-type-delete-popup',
    template: ''
})
export class LanguageTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private languageTypePopupService: LanguageTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.languageTypePopupService
                .open(LanguageTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
