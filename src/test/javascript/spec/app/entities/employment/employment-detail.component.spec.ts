import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EmploymentDetailComponent } from '../../../../../../main/webapp/app/entities/employment/employment-detail.component';
import { EmploymentService } from '../../../../../../main/webapp/app/entities/employment/employment.service';
import { Employment } from '../../../../../../main/webapp/app/entities/employment/employment.model';

describe('Component Tests', () => {

    describe('Employment Management Detail Component', () => {
        let comp: EmploymentDetailComponent;
        let fixture: ComponentFixture<EmploymentDetailComponent>;
        let service: EmploymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [EmploymentDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EmploymentService,
                    EventManager
                ]
            }).overrideComponent(EmploymentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmploymentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmploymentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Employment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.employment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
