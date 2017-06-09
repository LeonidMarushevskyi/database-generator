import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PosessionType } from './posession-type.model';
import { PosessionTypeService } from './posession-type.service';

@Component({
    selector: 'jhi-posession-type-detail',
    templateUrl: './posession-type-detail.component.html'
})
export class PosessionTypeDetailComponent implements OnInit, OnDestroy {

    posessionType: PosessionType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private posessionTypeService: PosessionTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPosessionTypes();
    }

    load(id) {
        this.posessionTypeService.find(id).subscribe((posessionType) => {
            this.posessionType = posessionType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPosessionTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'posessionTypeListModification',
            (response) => this.load(this.posessionType.id)
        );
    }
}
