import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FacilityDetailComponent } from '../../../../../../main/webapp/app/entities/facility/facility-detail.component';
import { FacilityService } from '../../../../../../main/webapp/app/entities/facility/facility.service';
import { Facility } from '../../../../../../main/webapp/app/entities/facility/facility.model';

describe('Component Tests', () => {

    describe('Facility Management Detail Component', () => {
        let comp: FacilityDetailComponent;
        let fixture: ComponentFixture<FacilityDetailComponent>;
        let service: FacilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [FacilityDetailComponent],
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
                    FacilityService
                ]
            }).overrideComponent(FacilityDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Facility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.facility).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
