import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CountyTypeDetailComponent } from '../../../../../../main/webapp/app/entities/county-type/county-type-detail.component';
import { CountyTypeService } from '../../../../../../main/webapp/app/entities/county-type/county-type.service';
import { CountyType } from '../../../../../../main/webapp/app/entities/county-type/county-type.model';

describe('Component Tests', () => {

    describe('CountyType Management Detail Component', () => {
        let comp: CountyTypeDetailComponent;
        let fixture: ComponentFixture<CountyTypeDetailComponent>;
        let service: CountyTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [CountyTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CountyTypeService,
                    EventManager
                ]
            }).overrideComponent(CountyTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CountyTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountyTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CountyType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.countyType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
