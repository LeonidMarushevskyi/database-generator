import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EmployerDetailComponent } from '../../../../../../main/webapp/app/entities/employer/employer-detail.component';
import { EmployerService } from '../../../../../../main/webapp/app/entities/employer/employer.service';
import { Employer } from '../../../../../../main/webapp/app/entities/employer/employer.model';

describe('Component Tests', () => {

    describe('Employer Management Detail Component', () => {
        let comp: EmployerDetailComponent;
        let fixture: ComponentFixture<EmployerDetailComponent>;
        let service: EmployerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [EmployerDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EmployerService,
                    EventManager
                ]
            }).overrideComponent(EmployerDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Employer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.employer).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
