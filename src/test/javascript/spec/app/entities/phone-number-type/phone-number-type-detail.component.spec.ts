import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PhoneNumberTypeDetailComponent } from '../../../../../../main/webapp/app/entities/phone-number-type/phone-number-type-detail.component';
import { PhoneNumberTypeService } from '../../../../../../main/webapp/app/entities/phone-number-type/phone-number-type.service';
import { PhoneNumberType } from '../../../../../../main/webapp/app/entities/phone-number-type/phone-number-type.model';

describe('Component Tests', () => {

    describe('PhoneNumberType Management Detail Component', () => {
        let comp: PhoneNumberTypeDetailComponent;
        let fixture: ComponentFixture<PhoneNumberTypeDetailComponent>;
        let service: PhoneNumberTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [PhoneNumberTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PhoneNumberTypeService,
                    EventManager
                ]
            }).overrideComponent(PhoneNumberTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhoneNumberTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhoneNumberTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PhoneNumberType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.phoneNumberType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
