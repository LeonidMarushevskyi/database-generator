import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonLanguageDetailComponent } from '../../../../../../main/webapp/app/entities/person-language/person-language-detail.component';
import { PersonLanguageService } from '../../../../../../main/webapp/app/entities/person-language/person-language.service';
import { PersonLanguage } from '../../../../../../main/webapp/app/entities/person-language/person-language.model';

describe('Component Tests', () => {

    describe('PersonLanguage Management Detail Component', () => {
        let comp: PersonLanguageDetailComponent;
        let fixture: ComponentFixture<PersonLanguageDetailComponent>;
        let service: PersonLanguageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [PersonLanguageDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonLanguageService,
                    EventManager
                ]
            }).overrideComponent(PersonLanguageDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonLanguageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonLanguageService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonLanguage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personLanguage).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
