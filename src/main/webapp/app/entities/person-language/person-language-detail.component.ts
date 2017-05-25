import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PersonLanguage } from './person-language.model';
import { PersonLanguageService } from './person-language.service';

@Component({
    selector: 'jhi-person-language-detail',
    templateUrl: './person-language-detail.component.html'
})
export class PersonLanguageDetailComponent implements OnInit, OnDestroy {

    personLanguage: PersonLanguage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private personLanguageService: PersonLanguageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonLanguages();
    }

    load(id) {
        this.personLanguageService.find(id).subscribe((personLanguage) => {
            this.personLanguage = personLanguage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonLanguages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personLanguageListModification',
            (response) => this.load(this.personLanguage.id)
        );
    }
}
