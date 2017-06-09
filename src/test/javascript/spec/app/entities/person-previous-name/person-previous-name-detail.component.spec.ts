import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonPreviousNameDetailComponent } from '../../../../../../main/webapp/app/entities/person-previous-name/person-previous-name-detail.component';
import { PersonPreviousNameService } from '../../../../../../main/webapp/app/entities/person-previous-name/person-previous-name.service';
import { PersonPreviousName } from '../../../../../../main/webapp/app/entities/person-previous-name/person-previous-name.model';

describe('Component Tests', () => {

    describe('PersonPreviousName Management Detail Component', () => {
        let comp: PersonPreviousNameDetailComponent;
        let fixture: ComponentFixture<PersonPreviousNameDetailComponent>;
        let service: PersonPreviousNameService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeneratorTestModule],
                declarations: [PersonPreviousNameDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonPreviousNameService,
                    EventManager
                ]
            }).overrideComponent(PersonPreviousNameDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonPreviousNameDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonPreviousNameService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonPreviousName(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personPreviousName).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
