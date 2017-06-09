import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BodyOfWaterDetailComponent } from '../../../../../../main/webapp/app/entities/body-of-water/body-of-water-detail.component';
import { BodyOfWaterService } from '../../../../../../main/webapp/app/entities/body-of-water/body-of-water.service';
import { BodyOfWater } from '../../../../../../main/webapp/app/entities/body-of-water/body-of-water.model';

describe('Component Tests', () => {

    describe('BodyOfWater Management Detail Component', () => {
        let comp: BodyOfWaterDetailComponent;
        let fixture: ComponentFixture<BodyOfWaterDetailComponent>;
        let service: BodyOfWaterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [BodyOfWaterDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BodyOfWaterService,
                    EventManager
                ]
            }).overrideComponent(BodyOfWaterDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BodyOfWaterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BodyOfWaterService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BodyOfWater(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bodyOfWater).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
