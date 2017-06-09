import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , DataUtils } from 'ng-jhipster';

import { CriminalRecord } from './criminal-record.model';
import { CriminalRecordService } from './criminal-record.service';

@Component({
    selector: 'jhi-criminal-record-detail',
    templateUrl: './criminal-record-detail.component.html'
})
export class CriminalRecordDetailComponent implements OnInit, OnDestroy {

    criminalRecord: CriminalRecord;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private criminalRecordService: CriminalRecordService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCriminalRecords();
    }

    load(id) {
        this.criminalRecordService.find(id).subscribe((criminalRecord) => {
            this.criminalRecord = criminalRecord;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCriminalRecords() {
        this.eventSubscriber = this.eventManager.subscribe(
            'criminalRecordListModification',
            (response) => this.load(this.criminalRecord.id)
        );
    }
}
