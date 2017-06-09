import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CriminalRecordDetailComponent } from '../../../../../../main/webapp/app/entities/criminal-record/criminal-record-detail.component';
import { CriminalRecordService } from '../../../../../../main/webapp/app/entities/criminal-record/criminal-record.service';
import { CriminalRecord } from '../../../../../../main/webapp/app/entities/criminal-record/criminal-record.model';

describe('Component Tests', () => {

    describe('CriminalRecord Management Detail Component', () => {
        let comp: CriminalRecordDetailComponent;
        let fixture: ComponentFixture<CriminalRecordDetailComponent>;
        let service: CriminalRecordService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [CriminalRecordDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CriminalRecordService,
                    EventManager
                ]
            }).overrideComponent(CriminalRecordDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CriminalRecordDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CriminalRecordService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CriminalRecord(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.criminalRecord).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
