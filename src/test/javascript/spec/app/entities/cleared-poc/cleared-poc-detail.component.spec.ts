import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClearedPOCDetailComponent } from '../../../../../../main/webapp/app/entities/cleared-poc/cleared-poc-detail.component';
import { ClearedPOCService } from '../../../../../../main/webapp/app/entities/cleared-poc/cleared-poc.service';
import { ClearedPOC } from '../../../../../../main/webapp/app/entities/cleared-poc/cleared-poc.model';

describe('Component Tests', () => {

    describe('ClearedPOC Management Detail Component', () => {
        let comp: ClearedPOCDetailComponent;
        let fixture: ComponentFixture<ClearedPOCDetailComponent>;
        let service: ClearedPOCService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [ClearedPOCDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClearedPOCService,
                    EventManager
                ]
            }).overrideComponent(ClearedPOCDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClearedPOCDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClearedPOCService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClearedPOC(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clearedPOC).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
