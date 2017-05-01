import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { LanguageType } from './language-type.model';
import { LanguageTypeService } from './language-type.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-language-type',
    templateUrl: './language-type.component.html'
})
export class LanguageTypeComponent implements OnInit, OnDestroy {
languageTypes: LanguageType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private languageTypeService: LanguageTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.languageTypeService.query().subscribe(
            (res: Response) => {
                this.languageTypes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLanguageTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: LanguageType) {
        return item.id;
    }



    registerChangeInLanguageTypes() {
        this.eventSubscriber = this.eventManager.subscribe('languageTypeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
