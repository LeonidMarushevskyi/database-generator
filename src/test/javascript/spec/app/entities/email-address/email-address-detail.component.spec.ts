import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EmailAddressDetailComponent } from '../../../../../../main/webapp/app/entities/email-address/email-address-detail.component';
import { EmailAddressService } from '../../../../../../main/webapp/app/entities/email-address/email-address.service';
import { EmailAddress } from '../../../../../../main/webapp/app/entities/email-address/email-address.model';

describe('Component Tests', () => {

    describe('EmailAddress Management Detail Component', () => {
        let comp: EmailAddressDetailComponent;
        let fixture: ComponentFixture<EmailAddressDetailComponent>;
        let service: EmailAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [EmailAddressDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EmailAddressService,
                    EventManager
                ]
            }).overrideComponent(EmailAddressDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmailAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailAddressService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EmailAddress(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.emailAddress).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
