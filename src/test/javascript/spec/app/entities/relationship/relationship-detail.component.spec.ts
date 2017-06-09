import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RelationshipDetailComponent } from '../../../../../../main/webapp/app/entities/relationship/relationship-detail.component';
import { RelationshipService } from '../../../../../../main/webapp/app/entities/relationship/relationship.service';
import { Relationship } from '../../../../../../main/webapp/app/entities/relationship/relationship.model';

describe('Component Tests', () => {

    describe('Relationship Management Detail Component', () => {
        let comp: RelationshipDetailComponent;
        let fixture: ComponentFixture<RelationshipDetailComponent>;
        let service: RelationshipService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [RelationshipDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RelationshipService,
                    EventManager
                ]
            }).overrideComponent(RelationshipDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RelationshipDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RelationshipService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Relationship(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.relationship).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
