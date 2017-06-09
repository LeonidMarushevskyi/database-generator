import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HouseholdChildDetailComponent } from '../../../../../../main/webapp/app/entities/household-child/household-child-detail.component';
import { HouseholdChildService } from '../../../../../../main/webapp/app/entities/household-child/household-child.service';
import { HouseholdChild } from '../../../../../../main/webapp/app/entities/household-child/household-child.model';

describe('Component Tests', () => {

    describe('HouseholdChild Management Detail Component', () => {
        let comp: HouseholdChildDetailComponent;
        let fixture: ComponentFixture<HouseholdChildDetailComponent>;
        let service: HouseholdChildService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [HouseholdChildDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HouseholdChildService,
                    EventManager
                ]
            }).overrideComponent(HouseholdChildDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HouseholdChildDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HouseholdChildService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HouseholdChild(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.householdChild).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
