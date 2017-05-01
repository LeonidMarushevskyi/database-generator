import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PersonLanguage } from './person-language.model';
import { PersonLanguagePopupService } from './person-language-popup.service';
import { PersonLanguageService } from './person-language.service';

@Component({
    selector: 'jhi-person-language-delete-dialog',
    templateUrl: './person-language-delete-dialog.component.html'
})
export class PersonLanguageDeleteDialogComponent {

    personLanguage: PersonLanguage;

    constructor(
        private personLanguageService: PersonLanguageService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.personLanguageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personLanguageListModification',
                content: 'Deleted an personLanguage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-person-language-delete-popup',
    template: ''
})
export class PersonLanguageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private personLanguagePopupService: PersonLanguagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.personLanguagePopupService
                .open(PersonLanguageDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
