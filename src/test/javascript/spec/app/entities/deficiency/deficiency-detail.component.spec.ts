import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DeficiencyDetailComponent } from '../../../../../../main/webapp/app/entities/deficiency/deficiency-detail.component';
import { DeficiencyService } from '../../../../../../main/webapp/app/entities/deficiency/deficiency.service';
import { Deficiency } from '../../../../../../main/webapp/app/entities/deficiency/deficiency.model';

describe('Component Tests', () => {

    describe('Deficiency Management Detail Component', () => {
        let comp: DeficiencyDetailComponent;
        let fixture: ComponentFixture<DeficiencyDetailComponent>;
        let service: DeficiencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [DeficiencyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DeficiencyService,
                    EventManager
                ]
            }).overrideComponent(DeficiencyDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeficiencyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeficiencyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Deficiency(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.deficiency).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
