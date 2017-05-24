import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonRaceDetailComponent } from '../../../../../../main/webapp/app/entities/person-race/person-race-detail.component';
import { PersonRaceService } from '../../../../../../main/webapp/app/entities/person-race/person-race.service';
import { PersonRace } from '../../../../../../main/webapp/app/entities/person-race/person-race.model';

describe('Component Tests', () => {

    describe('PersonRace Management Detail Component', () => {
        let comp: PersonRaceDetailComponent;
        let fixture: ComponentFixture<PersonRaceDetailComponent>;
        let service: PersonRaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PersonRaceDetailComponent],
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
                    PersonRaceService
                ]
            }).overrideComponent(PersonRaceDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonRaceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonRaceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonRace(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personRace).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
