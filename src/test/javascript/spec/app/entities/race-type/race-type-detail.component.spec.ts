import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RaceTypeDetailComponent } from '../../../../../../main/webapp/app/entities/race-type/race-type-detail.component';
import { RaceTypeService } from '../../../../../../main/webapp/app/entities/race-type/race-type.service';
import { RaceType } from '../../../../../../main/webapp/app/entities/race-type/race-type.model';

describe('Component Tests', () => {

    describe('RaceType Management Detail Component', () => {
        let comp: RaceTypeDetailComponent;
        let fixture: ComponentFixture<RaceTypeDetailComponent>;
        let service: RaceTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [RaceTypeDetailComponent],
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
                    RaceTypeService
                ]
            }).overrideComponent(RaceTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RaceTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaceTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RaceType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.raceType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
