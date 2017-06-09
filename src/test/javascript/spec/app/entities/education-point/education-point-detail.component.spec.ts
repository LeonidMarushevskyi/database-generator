import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EducationPointDetailComponent } from '../../../../../../main/webapp/app/entities/education-point/education-point-detail.component';
import { EducationPointService } from '../../../../../../main/webapp/app/entities/education-point/education-point.service';
import { EducationPoint } from '../../../../../../main/webapp/app/entities/education-point/education-point.model';

describe('Component Tests', () => {

    describe('EducationPoint Management Detail Component', () => {
        let comp: EducationPointDetailComponent;
        let fixture: ComponentFixture<EducationPointDetailComponent>;
        let service: EducationPointService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [EducationPointDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EducationPointService,
                    EventManager
                ]
            }).overrideComponent(EducationPointDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EducationPointDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EducationPointService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EducationPoint(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.educationPoint).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
