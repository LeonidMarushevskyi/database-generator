import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonAddressDetailComponent } from '../../../../../../main/webapp/app/entities/person-address/person-address-detail.component';
import { PersonAddressService } from '../../../../../../main/webapp/app/entities/person-address/person-address.service';
import { PersonAddress } from '../../../../../../main/webapp/app/entities/person-address/person-address.model';

describe('Component Tests', () => {

    describe('PersonAddress Management Detail Component', () => {
        let comp: PersonAddressDetailComponent;
        let fixture: ComponentFixture<PersonAddressDetailComponent>;
        let service: PersonAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [PersonAddressDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonAddressService,
                    EventManager
                ]
            }).overrideComponent(PersonAddressDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonAddressService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonAddress(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personAddress).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
