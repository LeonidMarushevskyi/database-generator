import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AppRelHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/app-rel-history/app-rel-history-detail.component';
import { AppRelHistoryService } from '../../../../../../main/webapp/app/entities/app-rel-history/app-rel-history.service';
import { AppRelHistory } from '../../../../../../main/webapp/app/entities/app-rel-history/app-rel-history.model';

describe('Component Tests', () => {

    describe('AppRelHistory Management Detail Component', () => {
        let comp: AppRelHistoryDetailComponent;
        let fixture: ComponentFixture<AppRelHistoryDetailComponent>;
        let service: AppRelHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [AppRelHistoryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AppRelHistoryService,
                    EventManager
                ]
            }).overrideComponent(AppRelHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppRelHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppRelHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AppRelHistory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.appRelHistory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
