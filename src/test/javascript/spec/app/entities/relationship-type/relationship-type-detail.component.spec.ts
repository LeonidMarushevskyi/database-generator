import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RelationshipTypeDetailComponent } from '../../../../../../main/webapp/app/entities/relationship-type/relationship-type-detail.component';
import { RelationshipTypeService } from '../../../../../../main/webapp/app/entities/relationship-type/relationship-type.service';
import { RelationshipType } from '../../../../../../main/webapp/app/entities/relationship-type/relationship-type.model';

describe('Component Tests', () => {

    describe('RelationshipType Management Detail Component', () => {
        let comp: RelationshipTypeDetailComponent;
        let fixture: ComponentFixture<RelationshipTypeDetailComponent>;
        let service: RelationshipTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [RelationshipTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RelationshipTypeService,
                    EventManager
                ]
            }).overrideComponent(RelationshipTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RelationshipTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RelationshipTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RelationshipType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.relationshipType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
