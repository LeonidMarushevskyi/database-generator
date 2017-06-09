import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HouseholdDetailComponent } from '../../../../../../main/webapp/app/entities/household/household-detail.component';
import { HouseholdService } from '../../../../../../main/webapp/app/entities/household/household.service';
import { Household } from '../../../../../../main/webapp/app/entities/household/household.model';

describe('Component Tests', () => {

    describe('Household Management Detail Component', () => {
        let comp: HouseholdDetailComponent;
        let fixture: ComponentFixture<HouseholdDetailComponent>;
        let service: HouseholdService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [HouseholdDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HouseholdService,
                    EventManager
                ]
            }).overrideComponent(HouseholdDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HouseholdDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HouseholdService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Household(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.household).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
