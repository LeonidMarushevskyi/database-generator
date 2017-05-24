import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AssignedWorkerDetailComponent } from '../../../../../../main/webapp/app/entities/assigned-worker/assigned-worker-detail.component';
import { AssignedWorkerService } from '../../../../../../main/webapp/app/entities/assigned-worker/assigned-worker.service';
import { AssignedWorker } from '../../../../../../main/webapp/app/entities/assigned-worker/assigned-worker.model';

describe('Component Tests', () => {

    describe('AssignedWorker Management Detail Component', () => {
        let comp: AssignedWorkerDetailComponent;
        let fixture: ComponentFixture<AssignedWorkerDetailComponent>;
        let service: AssignedWorkerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [AssignedWorkerDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    AssignedWorkerService
                ]
            }).overrideComponent(AssignedWorkerDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssignedWorkerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssignedWorkerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AssignedWorker(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.assignedWorker).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
