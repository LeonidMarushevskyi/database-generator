import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DeterminedChildDetailComponent } from '../../../../../../main/webapp/app/entities/determined-child/determined-child-detail.component';
import { DeterminedChildService } from '../../../../../../main/webapp/app/entities/determined-child/determined-child.service';
import { DeterminedChild } from '../../../../../../main/webapp/app/entities/determined-child/determined-child.model';

describe('Component Tests', () => {

    describe('DeterminedChild Management Detail Component', () => {
        let comp: DeterminedChildDetailComponent;
        let fixture: ComponentFixture<DeterminedChildDetailComponent>;
        let service: DeterminedChildService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [DeterminedChildDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DeterminedChildService,
                    EventManager
                ]
            }).overrideComponent(DeterminedChildDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeterminedChildDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeterminedChildService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DeterminedChild(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.determinedChild).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
