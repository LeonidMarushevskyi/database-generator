import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EducationLevelTypeDetailComponent } from '../../../../../../main/webapp/app/entities/education-level-type/education-level-type-detail.component';
import { EducationLevelTypeService } from '../../../../../../main/webapp/app/entities/education-level-type/education-level-type.service';
import { EducationLevelType } from '../../../../../../main/webapp/app/entities/education-level-type/education-level-type.model';

describe('Component Tests', () => {

    describe('EducationLevelType Management Detail Component', () => {
        let comp: EducationLevelTypeDetailComponent;
        let fixture: ComponentFixture<EducationLevelTypeDetailComponent>;
        let service: EducationLevelTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [EducationLevelTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EducationLevelTypeService,
                    EventManager
                ]
            }).overrideComponent(EducationLevelTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EducationLevelTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EducationLevelTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EducationLevelType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.educationLevelType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
