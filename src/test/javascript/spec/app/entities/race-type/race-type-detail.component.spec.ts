import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
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
                imports: [GeneratorTestModule],
                declarations: [RaceTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RaceTypeService,
                    EventManager
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
