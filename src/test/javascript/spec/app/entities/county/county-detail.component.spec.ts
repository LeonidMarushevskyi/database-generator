import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CountyDetailComponent } from '../../../../../../main/webapp/app/entities/county/county-detail.component';
import { CountyService } from '../../../../../../main/webapp/app/entities/county/county.service';
import { County } from '../../../../../../main/webapp/app/entities/county/county.model';

describe('Component Tests', () => {

    describe('County Management Detail Component', () => {
        let comp: CountyDetailComponent;
        let fixture: ComponentFixture<CountyDetailComponent>;
        let service: CountyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [CountyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CountyService,
                    EventManager
                ]
            }).overrideComponent(CountyDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CountyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new County(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.county).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
