import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ComplaintDetailComponent } from '../../../../../../main/webapp/app/entities/complaint/complaint-detail.component';
import { ComplaintService } from '../../../../../../main/webapp/app/entities/complaint/complaint.service';
import { Complaint } from '../../../../../../main/webapp/app/entities/complaint/complaint.model';

describe('Component Tests', () => {

    describe('Complaint Management Detail Component', () => {
        let comp: ComplaintDetailComponent;
        let fixture: ComponentFixture<ComplaintDetailComponent>;
        let service: ComplaintService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [ComplaintDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ComplaintService,
                    EventManager
                ]
            }).overrideComponent(ComplaintDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ComplaintDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComplaintService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Complaint(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.complaint).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
