import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FacilityStatusDetailComponent } from '../../../../../../main/webapp/app/entities/facility-status/facility-status-detail.component';
import { FacilityStatusService } from '../../../../../../main/webapp/app/entities/facility-status/facility-status.service';
import { FacilityStatus } from '../../../../../../main/webapp/app/entities/facility-status/facility-status.model';

describe('Component Tests', () => {

    describe('FacilityStatus Management Detail Component', () => {
        let comp: FacilityStatusDetailComponent;
        let fixture: ComponentFixture<FacilityStatusDetailComponent>;
        let service: FacilityStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [FacilityStatusDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FacilityStatusService,
                    EventManager
                ]
            }).overrideComponent(FacilityStatusDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityStatusService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FacilityStatus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.facilityStatus).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
