import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'ng-jhipster';
import { A } from './a.model';
import { AService } from './a.service';

@Component({
    selector: 'jhi-a-detail',
    templateUrl: './a-detail.component.html'
})
export class ADetailComponent implements OnInit, OnDestroy {

    a: A;
    private subscription: any;

    constructor(
        private dataUtils: DataUtils,
        private aService: AService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.aService.find(id).subscribe(a => {
            this.a = a;
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
    }

}
