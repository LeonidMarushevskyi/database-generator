import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FacilityChildDetailComponent } from '../../../../../../main/webapp/app/entities/facility-child/facility-child-detail.component';
import { FacilityChildService } from '../../../../../../main/webapp/app/entities/facility-child/facility-child.service';
import { FacilityChild } from '../../../../../../main/webapp/app/entities/facility-child/facility-child.model';

describe('Component Tests', () => {

    describe('FacilityChild Management Detail Component', () => {
        let comp: FacilityChildDetailComponent;
        let fixture: ComponentFixture<FacilityChildDetailComponent>;
        let service: FacilityChildService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [FacilityChildDetailComponent],
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
                    FacilityChildService
                ]
            }).overrideComponent(FacilityChildDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityChildDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityChildService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FacilityChild(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.facilityChild).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
