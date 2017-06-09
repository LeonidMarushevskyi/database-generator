import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PosessionTypeDetailComponent } from '../../../../../../main/webapp/app/entities/posession-type/posession-type-detail.component';
import { PosessionTypeService } from '../../../../../../main/webapp/app/entities/posession-type/posession-type.service';
import { PosessionType } from '../../../../../../main/webapp/app/entities/posession-type/posession-type.model';

describe('Component Tests', () => {

    describe('PosessionType Management Detail Component', () => {
        let comp: PosessionTypeDetailComponent;
        let fixture: ComponentFixture<PosessionTypeDetailComponent>;
        let service: PosessionTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [PosessionTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PosessionTypeService,
                    EventManager
                ]
            }).overrideComponent(PosessionTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PosessionTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PosessionTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PosessionType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.posessionType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
