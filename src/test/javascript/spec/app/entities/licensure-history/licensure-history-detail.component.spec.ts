import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LicensureHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/licensure-history/licensure-history-detail.component';
import { LicensureHistoryService } from '../../../../../../main/webapp/app/entities/licensure-history/licensure-history.service';
import { LicensureHistory } from '../../../../../../main/webapp/app/entities/licensure-history/licensure-history.model';

describe('Component Tests', () => {

    describe('LicensureHistory Management Detail Component', () => {
        let comp: LicensureHistoryDetailComponent;
        let fixture: ComponentFixture<LicensureHistoryDetailComponent>;
        let service: LicensureHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [LicensureHistoryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LicensureHistoryService,
                    EventManager
                ]
            }).overrideComponent(LicensureHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LicensureHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LicensureHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LicensureHistory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.licensureHistory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
