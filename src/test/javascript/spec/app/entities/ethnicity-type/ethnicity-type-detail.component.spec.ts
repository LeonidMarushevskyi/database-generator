import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EthnicityTypeDetailComponent } from '../../../../../../main/webapp/app/entities/ethnicity-type/ethnicity-type-detail.component';
import { EthnicityTypeService } from '../../../../../../main/webapp/app/entities/ethnicity-type/ethnicity-type.service';
import { EthnicityType } from '../../../../../../main/webapp/app/entities/ethnicity-type/ethnicity-type.model';

describe('Component Tests', () => {

    describe('EthnicityType Management Detail Component', () => {
        let comp: EthnicityTypeDetailComponent;
        let fixture: ComponentFixture<EthnicityTypeDetailComponent>;
        let service: EthnicityTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [EthnicityTypeDetailComponent],
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
                    EthnicityTypeService
                ]
            }).overrideComponent(EthnicityTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EthnicityTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EthnicityTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EthnicityType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ethnicityType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
