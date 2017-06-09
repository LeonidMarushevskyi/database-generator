import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GenderTypeDetailComponent } from '../../../../../../main/webapp/app/entities/gender-type/gender-type-detail.component';
import { GenderTypeService } from '../../../../../../main/webapp/app/entities/gender-type/gender-type.service';
import { GenderType } from '../../../../../../main/webapp/app/entities/gender-type/gender-type.model';

describe('Component Tests', () => {

    describe('GenderType Management Detail Component', () => {
        let comp: GenderTypeDetailComponent;
        let fixture: ComponentFixture<GenderTypeDetailComponent>;
        let service: GenderTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [GenderTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GenderTypeService,
                    EventManager
                ]
            }).overrideComponent(GenderTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GenderTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenderTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GenderType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.genderType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
