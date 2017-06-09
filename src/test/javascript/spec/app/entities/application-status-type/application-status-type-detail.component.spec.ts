import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ApplicationStatusTypeDetailComponent } from '../../../../../../main/webapp/app/entities/application-status-type/application-status-type-detail.component';
import { ApplicationStatusTypeService } from '../../../../../../main/webapp/app/entities/application-status-type/application-status-type.service';
import { ApplicationStatusType } from '../../../../../../main/webapp/app/entities/application-status-type/application-status-type.model';

describe('Component Tests', () => {

    describe('ApplicationStatusType Management Detail Component', () => {
        let comp: ApplicationStatusTypeDetailComponent;
        let fixture: ComponentFixture<ApplicationStatusTypeDetailComponent>;
        let service: ApplicationStatusTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [ApplicationStatusTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ApplicationStatusTypeService,
                    EventManager
                ]
            }).overrideComponent(ApplicationStatusTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApplicationStatusTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationStatusTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ApplicationStatusType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.applicationStatusType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
