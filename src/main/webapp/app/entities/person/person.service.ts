import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Person } from './person.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonService {

    private resourceUrl = 'api/people';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(person: Person): Observable<Person> {
        const copy = this.convert(person);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(person: Person): Observable<Person> {
        const copy = this.convert(person);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Person> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.dateOfBirth = this.dateUtils
            .convertLocalDateFromServer(entity.dateOfBirth);
        entity.createDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.createDateTime);
        entity.updateDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.updateDateTime);
    }

    private convert(person: Person): Person {
        const copy: Person = Object.assign({}, person);
        copy.dateOfBirth = this.dateUtils
            .convertLocalDateToServer(person.dateOfBirth);

        copy.createDateTime = this.dateUtils.toDate(person.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(person.updateDateTime);
        return copy;
    }
}
