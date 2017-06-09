import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { StateTypeDetailComponent } from '../../../../../../main/webapp/app/entities/state-type/state-type-detail.component';
import { StateTypeService } from '../../../../../../main/webapp/app/entities/state-type/state-type.service';
import { StateType } from '../../../../../../main/webapp/app/entities/state-type/state-type.model';

describe('Component Tests', () => {

    describe('StateType Management Detail Component', () => {
        let comp: StateTypeDetailComponent;
        let fixture: ComponentFixture<StateTypeDetailComponent>;
        let service: StateTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [StateTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    StateTypeService,
                    EventManager
                ]
            }).overrideComponent(StateTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StateTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StateTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new StateType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.stateType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
