import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ApplicationStatusType } from './application-status-type.model';
import { ApplicationStatusTypeService } from './application-status-type.service';

@Component({
    selector: 'jhi-application-status-type-detail',
    templateUrl: './application-status-type-detail.component.html'
})
export class ApplicationStatusTypeDetailComponent implements OnInit, OnDestroy {

    applicationStatusType: ApplicationStatusType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private applicationStatusTypeService: ApplicationStatusTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInApplicationStatusTypes();
    }

    load(id) {
        this.applicationStatusTypeService.find(id).subscribe((applicationStatusType) => {
            this.applicationStatusType = applicationStatusType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInApplicationStatusTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'applicationStatusTypeListModification',
            (response) => this.load(this.applicationStatusType.id)
        );
    }
}
