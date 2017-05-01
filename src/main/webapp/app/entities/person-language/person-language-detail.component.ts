import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PersonLanguage } from './person-language.model';
import { PersonLanguageService } from './person-language.service';

@Component({
    selector: 'jhi-person-language-detail',
    templateUrl: './person-language-detail.component.html'
})
export class PersonLanguageDetailComponent implements OnInit, OnDestroy {

    personLanguage: PersonLanguage;
    private subscription: any;

    constructor(
        private personLanguageService: PersonLanguageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.personLanguageService.find(id).subscribe(personLanguage => {
            this.personLanguage = personLanguage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
