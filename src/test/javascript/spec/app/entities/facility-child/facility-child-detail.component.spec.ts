import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FacilityChildDetailComponent } from '../../../../../../main/webapp/app/entities/facility-child/facility-child-detail.component';
import { FacilityChildService } from '../../../../../../main/webapp/app/entities/facility-child/facility-child.service';
import { FacilityChild } from '../../../../../../main/webapp/app/entities/facility-child/facility-child.model';

describe('Component Tests', () => {

    describe('FacilityChild Management Detail Component', () => {
        let comp: FacilityChildDetailComponent;
        let fixture: ComponentFixture<FacilityChildDetailComponent>;
        let service: FacilityChildService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [FacilityChildDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FacilityChildService,
                    EventManager
                ]
            }).overrideComponent(FacilityChildDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityChildDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityChildService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FacilityChild(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.facilityChild).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
