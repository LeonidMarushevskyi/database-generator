import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PersonEthnicity } from './person-ethnicity.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonEthnicityService {

    private resourceUrl = 'api/person-ethnicities';

    constructor(private http: Http) { }

    create(personEthnicity: PersonEthnicity): Observable<PersonEthnicity> {
        const copy = this.convert(personEthnicity);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(personEthnicity: PersonEthnicity): Observable<PersonEthnicity> {
        const copy = this.convert(personEthnicity);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PersonEthnicity> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(personEthnicity: PersonEthnicity): PersonEthnicity {
        const copy: PersonEthnicity = Object.assign({}, personEthnicity);
        return copy;
    }
}
