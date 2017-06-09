import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AgeGroupTypeDetailComponent } from '../../../../../../main/webapp/app/entities/age-group-type/age-group-type-detail.component';
import { AgeGroupTypeService } from '../../../../../../main/webapp/app/entities/age-group-type/age-group-type.service';
import { AgeGroupType } from '../../../../../../main/webapp/app/entities/age-group-type/age-group-type.model';

describe('Component Tests', () => {

    describe('AgeGroupType Management Detail Component', () => {
        let comp: AgeGroupTypeDetailComponent;
        let fixture: ComponentFixture<AgeGroupTypeDetailComponent>;
        let service: AgeGroupTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [AgeGroupTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AgeGroupTypeService,
                    EventManager
                ]
            }).overrideComponent(AgeGroupTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgeGroupTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgeGroupTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AgeGroupType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ageGroupType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
