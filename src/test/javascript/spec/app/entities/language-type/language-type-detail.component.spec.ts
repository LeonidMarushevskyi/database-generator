import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LanguageTypeDetailComponent } from '../../../../../../main/webapp/app/entities/language-type/language-type-detail.component';
import { LanguageTypeService } from '../../../../../../main/webapp/app/entities/language-type/language-type.service';
import { LanguageType } from '../../../../../../main/webapp/app/entities/language-type/language-type.model';

describe('Component Tests', () => {

    describe('LanguageType Management Detail Component', () => {
        let comp: LanguageTypeDetailComponent;
        let fixture: ComponentFixture<LanguageTypeDetailComponent>;
        let service: LanguageTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [LanguageTypeDetailComponent],
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
                    LanguageTypeService
                ]
            }).overrideComponent(LanguageTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LanguageTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LanguageTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LanguageType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.languageType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
