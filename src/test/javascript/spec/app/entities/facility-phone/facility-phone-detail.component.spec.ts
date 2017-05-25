import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FacilityPhoneDetailComponent } from '../../../../../../main/webapp/app/entities/facility-phone/facility-phone-detail.component';
import { FacilityPhoneService } from '../../../../../../main/webapp/app/entities/facility-phone/facility-phone.service';
import { FacilityPhone } from '../../../../../../main/webapp/app/entities/facility-phone/facility-phone.model';

describe('Component Tests', () => {

    describe('FacilityPhone Management Detail Component', () => {
        let comp: FacilityPhoneDetailComponent;
        let fixture: ComponentFixture<FacilityPhoneDetailComponent>;
        let service: FacilityPhoneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [FacilityPhoneDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FacilityPhoneService,
                    EventManager
                ]
            }).overrideComponent(FacilityPhoneDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityPhoneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityPhoneService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FacilityPhone(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.facilityPhone).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
