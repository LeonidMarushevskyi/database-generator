import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FacilityAddressDetailComponent } from '../../../../../../main/webapp/app/entities/facility-address/facility-address-detail.component';
import { FacilityAddressService } from '../../../../../../main/webapp/app/entities/facility-address/facility-address.service';
import { FacilityAddress } from '../../../../../../main/webapp/app/entities/facility-address/facility-address.model';

describe('Component Tests', () => {

    describe('FacilityAddress Management Detail Component', () => {
        let comp: FacilityAddressDetailComponent;
        let fixture: ComponentFixture<FacilityAddressDetailComponent>;
        let service: FacilityAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [FacilityAddressDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FacilityAddressService,
                    EventManager
                ]
            }).overrideComponent(FacilityAddressDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityAddressService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FacilityAddress(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.facilityAddress).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
