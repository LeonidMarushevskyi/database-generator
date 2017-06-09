import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RelationshipEventDetailComponent } from '../../../../../../main/webapp/app/entities/relationship-event/relationship-event-detail.component';
import { RelationshipEventService } from '../../../../../../main/webapp/app/entities/relationship-event/relationship-event.service';
import { RelationshipEvent } from '../../../../../../main/webapp/app/entities/relationship-event/relationship-event.model';

describe('Component Tests', () => {

    describe('RelationshipEvent Management Detail Component', () => {
        let comp: RelationshipEventDetailComponent;
        let fixture: ComponentFixture<RelationshipEventDetailComponent>;
        let service: RelationshipEventService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [RelationshipEventDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RelationshipEventService,
                    EventManager
                ]
            }).overrideComponent(RelationshipEventDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RelationshipEventDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RelationshipEventService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RelationshipEvent(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.relationshipEvent).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
