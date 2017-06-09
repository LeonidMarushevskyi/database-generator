import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PhoneNumberDetailComponent } from '../../../../../../main/webapp/app/entities/phone-number/phone-number-detail.component';
import { PhoneNumberService } from '../../../../../../main/webapp/app/entities/phone-number/phone-number.service';
import { PhoneNumber } from '../../../../../../main/webapp/app/entities/phone-number/phone-number.model';

describe('Component Tests', () => {

    describe('PhoneNumber Management Detail Component', () => {
        let comp: PhoneNumberDetailComponent;
        let fixture: ComponentFixture<PhoneNumberDetailComponent>;
        let service: PhoneNumberService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [PhoneNumberDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PhoneNumberService,
                    EventManager
                ]
            }).overrideComponent(PhoneNumberDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhoneNumberDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhoneNumberService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PhoneNumber(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.phoneNumber).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
