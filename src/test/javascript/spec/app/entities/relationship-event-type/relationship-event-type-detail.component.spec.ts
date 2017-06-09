import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RelationshipEventTypeDetailComponent } from '../../../../../../main/webapp/app/entities/relationship-event-type/relationship-event-type-detail.component';
import { RelationshipEventTypeService } from '../../../../../../main/webapp/app/entities/relationship-event-type/relationship-event-type.service';
import { RelationshipEventType } from '../../../../../../main/webapp/app/entities/relationship-event-type/relationship-event-type.model';

describe('Component Tests', () => {

    describe('RelationshipEventType Management Detail Component', () => {
        let comp: RelationshipEventTypeDetailComponent;
        let fixture: ComponentFixture<RelationshipEventTypeDetailComponent>;
        let service: RelationshipEventTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [RelationshipEventTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RelationshipEventTypeService,
                    EventManager
                ]
            }).overrideComponent(RelationshipEventTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RelationshipEventTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RelationshipEventTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RelationshipEventType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.relationshipEventType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
