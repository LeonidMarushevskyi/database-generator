import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { PersonLanguage } from './person-language.model';
import { PersonLanguageService } from './person-language.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-person-language',
    templateUrl: './person-language.component.html'
})
export class PersonLanguageComponent implements OnInit, OnDestroy {
personLanguages: PersonLanguage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personLanguageService: PersonLanguageService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.personLanguageService.query().subscribe(
            (res: ResponseWrapper) => {
                this.personLanguages = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonLanguages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PersonLanguage) {
        return item.id;
    }
    registerChangeInPersonLanguages() {
        this.eventSubscriber = this.eventManager.subscribe('personLanguageListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
