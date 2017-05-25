import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ADetailComponent } from '../../../../../../main/webapp/app/entities/a/a-detail.component';
import { AService } from '../../../../../../main/webapp/app/entities/a/a.service';
import { A } from '../../../../../../main/webapp/app/entities/a/a.model';

describe('Component Tests', () => {

    describe('A Management Detail Component', () => {
        let comp: ADetailComponent;
        let fixture: ComponentFixture<ADetailComponent>;
        let service: AService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [ADetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AService,
                    EventManager
                ]
            }).overrideComponent(ADetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ADetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new A(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.a).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
