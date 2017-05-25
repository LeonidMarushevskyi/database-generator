import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FacilityTypeDetailComponent } from '../../../../../../main/webapp/app/entities/facility-type/facility-type-detail.component';
import { FacilityTypeService } from '../../../../../../main/webapp/app/entities/facility-type/facility-type.service';
import { FacilityType } from '../../../../../../main/webapp/app/entities/facility-type/facility-type.model';

describe('Component Tests', () => {

    describe('FacilityType Management Detail Component', () => {
        let comp: FacilityTypeDetailComponent;
        let fixture: ComponentFixture<FacilityTypeDetailComponent>;
        let service: FacilityTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [FacilityTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FacilityTypeService,
                    EventManager
                ]
            }).overrideComponent(FacilityTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FacilityType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.facilityType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
