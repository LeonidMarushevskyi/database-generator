import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ApplicantDetailComponent } from '../../../../../../main/webapp/app/entities/applicant/applicant-detail.component';
import { ApplicantService } from '../../../../../../main/webapp/app/entities/applicant/applicant.service';
import { Applicant } from '../../../../../../main/webapp/app/entities/applicant/applicant.model';

describe('Component Tests', () => {

    describe('Applicant Management Detail Component', () => {
        let comp: ApplicantDetailComponent;
        let fixture: ComponentFixture<ApplicantDetailComponent>;
        let service: ApplicantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [ApplicantDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ApplicantService,
                    EventManager
                ]
            }).overrideComponent(ApplicantDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApplicantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicantService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Applicant(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.applicant).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
