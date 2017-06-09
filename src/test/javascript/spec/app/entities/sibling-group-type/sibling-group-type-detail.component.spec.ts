import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SiblingGroupTypeDetailComponent } from '../../../../../../main/webapp/app/entities/sibling-group-type/sibling-group-type-detail.component';
import { SiblingGroupTypeService } from '../../../../../../main/webapp/app/entities/sibling-group-type/sibling-group-type.service';
import { SiblingGroupType } from '../../../../../../main/webapp/app/entities/sibling-group-type/sibling-group-type.model';

describe('Component Tests', () => {

    describe('SiblingGroupType Management Detail Component', () => {
        let comp: SiblingGroupTypeDetailComponent;
        let fixture: ComponentFixture<SiblingGroupTypeDetailComponent>;
        let service: SiblingGroupTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [SiblingGroupTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SiblingGroupTypeService,
                    EventManager
                ]
            }).overrideComponent(SiblingGroupTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SiblingGroupTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiblingGroupTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SiblingGroupType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.siblingGroupType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
