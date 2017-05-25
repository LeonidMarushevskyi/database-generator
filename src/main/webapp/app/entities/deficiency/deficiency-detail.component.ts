import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Deficiency } from './deficiency.model';
import { DeficiencyService } from './deficiency.service';

@Component({
    selector: 'jhi-deficiency-detail',
    templateUrl: './deficiency-detail.component.html'
})
export class DeficiencyDetailComponent implements OnInit, OnDestroy {

    deficiency: Deficiency;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private deficiencyService: DeficiencyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDeficiencies();
    }

    load(id) {
        this.deficiencyService.find(id).subscribe((deficiency) => {
            this.deficiency = deficiency;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDeficiencies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'deficiencyListModification',
            (response) => this.load(this.deficiency.id)
        );
    }
}
