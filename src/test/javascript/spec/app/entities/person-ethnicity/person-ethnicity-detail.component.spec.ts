import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonEthnicityDetailComponent } from '../../../../../../main/webapp/app/entities/person-ethnicity/person-ethnicity-detail.component';
import { PersonEthnicityService } from '../../../../../../main/webapp/app/entities/person-ethnicity/person-ethnicity.service';
import { PersonEthnicity } from '../../../../../../main/webapp/app/entities/person-ethnicity/person-ethnicity.model';

describe('Component Tests', () => {

    describe('PersonEthnicity Management Detail Component', () => {
        let comp: PersonEthnicityDetailComponent;
        let fixture: ComponentFixture<PersonEthnicityDetailComponent>;
        let service: PersonEthnicityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PersonEthnicityDetailComponent],
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
                    PersonEthnicityService
                ]
            }).overrideComponent(PersonEthnicityDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonEthnicityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonEthnicityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonEthnicity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personEthnicity).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
