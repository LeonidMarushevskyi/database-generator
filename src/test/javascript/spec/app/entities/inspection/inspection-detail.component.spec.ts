import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InspectionDetailComponent } from '../../../../../../main/webapp/app/entities/inspection/inspection-detail.component';
import { InspectionService } from '../../../../../../main/webapp/app/entities/inspection/inspection.service';
import { Inspection } from '../../../../../../main/webapp/app/entities/inspection/inspection.model';

describe('Component Tests', () => {

    describe('Inspection Management Detail Component', () => {
        let comp: InspectionDetailComponent;
        let fixture: ComponentFixture<InspectionDetailComponent>;
        let service: InspectionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [InspectionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InspectionService,
                    EventManager
                ]
            }).overrideComponent(InspectionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InspectionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InspectionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Inspection(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inspection).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
