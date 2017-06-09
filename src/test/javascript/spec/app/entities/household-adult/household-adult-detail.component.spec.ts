import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HouseholdAdultDetailComponent } from '../../../../../../main/webapp/app/entities/household-adult/household-adult-detail.component';
import { HouseholdAdultService } from '../../../../../../main/webapp/app/entities/household-adult/household-adult.service';
import { HouseholdAdult } from '../../../../../../main/webapp/app/entities/household-adult/household-adult.model';

describe('Component Tests', () => {

    describe('HouseholdAdult Management Detail Component', () => {
        let comp: HouseholdAdultDetailComponent;
        let fixture: ComponentFixture<HouseholdAdultDetailComponent>;
        let service: HouseholdAdultService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [HouseholdAdultDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HouseholdAdultService,
                    EventManager
                ]
            }).overrideComponent(HouseholdAdultDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HouseholdAdultDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HouseholdAdultService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HouseholdAdult(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.householdAdult).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
