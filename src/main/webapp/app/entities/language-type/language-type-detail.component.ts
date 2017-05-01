import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LanguageType } from './language-type.model';
import { LanguageTypeService } from './language-type.service';

@Component({
    selector: 'jhi-language-type-detail',
    templateUrl: './language-type-detail.component.html'
})
export class LanguageTypeDetailComponent implements OnInit, OnDestroy {

    languageType: LanguageType;
    private subscription: any;

    constructor(
        private languageTypeService: LanguageTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.languageTypeService.find(id).subscribe(languageType => {
            this.languageType = languageType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
