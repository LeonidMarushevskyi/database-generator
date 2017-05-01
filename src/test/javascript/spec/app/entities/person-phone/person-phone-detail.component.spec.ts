import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonPhoneDetailComponent } from '../../../../../../main/webapp/app/entities/person-phone/person-phone-detail.component';
import { PersonPhoneService } from '../../../../../../main/webapp/app/entities/person-phone/person-phone.service';
import { PersonPhone } from '../../../../../../main/webapp/app/entities/person-phone/person-phone.model';

describe('Component Tests', () => {

    describe('PersonPhone Management Detail Component', () => {
        let comp: PersonPhoneDetailComponent;
        let fixture: ComponentFixture<PersonPhoneDetailComponent>;
        let service: PersonPhoneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PersonPhoneDetailComponent],
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
                    PersonPhoneService
                ]
            }).overrideComponent(PersonPhoneDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonPhoneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonPhoneService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonPhone(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personPhone).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
