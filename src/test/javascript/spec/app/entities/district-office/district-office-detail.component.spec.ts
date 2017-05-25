import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DistrictOfficeDetailComponent } from '../../../../../../main/webapp/app/entities/district-office/district-office-detail.component';
import { DistrictOfficeService } from '../../../../../../main/webapp/app/entities/district-office/district-office.service';
import { DistrictOffice } from '../../../../../../main/webapp/app/entities/district-office/district-office.model';

describe('Component Tests', () => {

    describe('DistrictOffice Management Detail Component', () => {
        let comp: DistrictOfficeDetailComponent;
        let fixture: ComponentFixture<DistrictOfficeDetailComponent>;
        let service: DistrictOfficeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [DistrictOfficeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DistrictOfficeService,
                    EventManager
                ]
            }).overrideComponent(DistrictOfficeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DistrictOfficeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DistrictOfficeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DistrictOffice(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.districtOffice).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
