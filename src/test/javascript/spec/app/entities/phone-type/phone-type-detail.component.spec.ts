import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PhoneTypeDetailComponent } from '../../../../../../main/webapp/app/entities/phone-type/phone-type-detail.component';
import { PhoneTypeService } from '../../../../../../main/webapp/app/entities/phone-type/phone-type.service';
import { PhoneType } from '../../../../../../main/webapp/app/entities/phone-type/phone-type.model';

describe('Component Tests', () => {

    describe('PhoneType Management Detail Component', () => {
        let comp: PhoneTypeDetailComponent;
        let fixture: ComponentFixture<PhoneTypeDetailComponent>;
        let service: PhoneTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [PhoneTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PhoneTypeService,
                    EventManager
                ]
            }).overrideComponent(PhoneTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhoneTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhoneTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PhoneType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.phoneType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
