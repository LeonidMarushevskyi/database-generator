import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
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
                declarations: [FacilityAddressDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    FacilityAddressService
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
