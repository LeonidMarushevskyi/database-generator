import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ChildPreferencesDetailComponent } from '../../../../../../main/webapp/app/entities/child-preferences/child-preferences-detail.component';
import { ChildPreferencesService } from '../../../../../../main/webapp/app/entities/child-preferences/child-preferences.service';
import { ChildPreferences } from '../../../../../../main/webapp/app/entities/child-preferences/child-preferences.model';

describe('Component Tests', () => {

    describe('ChildPreferences Management Detail Component', () => {
        let comp: ChildPreferencesDetailComponent;
        let fixture: ComponentFixture<ChildPreferencesDetailComponent>;
        let service: ChildPreferencesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [ChildPreferencesDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ChildPreferencesService,
                    EventManager
                ]
            }).overrideComponent(ChildPreferencesDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChildPreferencesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChildPreferencesService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ChildPreferences(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.childPreferences).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
