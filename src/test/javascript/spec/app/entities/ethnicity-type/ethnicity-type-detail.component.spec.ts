import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EthnicityTypeDetailComponent } from '../../../../../../main/webapp/app/entities/ethnicity-type/ethnicity-type-detail.component';
import { EthnicityTypeService } from '../../../../../../main/webapp/app/entities/ethnicity-type/ethnicity-type.service';
import { EthnicityType } from '../../../../../../main/webapp/app/entities/ethnicity-type/ethnicity-type.model';

describe('Component Tests', () => {

    describe('EthnicityType Management Detail Component', () => {
        let comp: EthnicityTypeDetailComponent;
        let fixture: ComponentFixture<EthnicityTypeDetailComponent>;
        let service: EthnicityTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [EthnicityTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EthnicityTypeService,
                    EventManager
                ]
            }).overrideComponent(EthnicityTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EthnicityTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EthnicityTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EthnicityType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ethnicityType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
