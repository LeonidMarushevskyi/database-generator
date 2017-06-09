import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HouseholdAddressDetailComponent } from '../../../../../../main/webapp/app/entities/household-address/household-address-detail.component';
import { HouseholdAddressService } from '../../../../../../main/webapp/app/entities/household-address/household-address.service';
import { HouseholdAddress } from '../../../../../../main/webapp/app/entities/household-address/household-address.model';

describe('Component Tests', () => {

    describe('HouseholdAddress Management Detail Component', () => {
        let comp: HouseholdAddressDetailComponent;
        let fixture: ComponentFixture<HouseholdAddressDetailComponent>;
        let service: HouseholdAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [HouseholdAddressDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HouseholdAddressService,
                    EventManager
                ]
            }).overrideComponent(HouseholdAddressDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HouseholdAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HouseholdAddressService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HouseholdAddress(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.householdAddress).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
