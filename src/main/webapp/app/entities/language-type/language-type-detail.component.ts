import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { LanguageType } from './language-type.model';
import { LanguageTypeService } from './language-type.service';

@Component({
    selector: 'jhi-language-type-detail',
    templateUrl: './language-type-detail.component.html'
})
export class LanguageTypeDetailComponent implements OnInit, OnDestroy {

    languageType: LanguageType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private languageTypeService: LanguageTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLanguageTypes();
    }

    load(id) {
        this.languageTypeService.find(id).subscribe((languageType) => {
            this.languageType = languageType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLanguageTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'languageTypeListModification',
            (response) => this.load(this.languageType.id)
        );
    }
}
