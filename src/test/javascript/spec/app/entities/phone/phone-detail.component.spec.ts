import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PhoneDetailComponent } from '../../../../../../main/webapp/app/entities/phone/phone-detail.component';
import { PhoneService } from '../../../../../../main/webapp/app/entities/phone/phone.service';
import { Phone } from '../../../../../../main/webapp/app/entities/phone/phone.model';

describe('Component Tests', () => {

    describe('Phone Management Detail Component', () => {
        let comp: PhoneDetailComponent;
        let fixture: ComponentFixture<PhoneDetailComponent>;
        let service: PhoneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [PhoneDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PhoneService,
                    EventManager
                ]
            }).overrideComponent(PhoneDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhoneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhoneService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Phone(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.phone).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
