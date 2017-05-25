import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PersonPhone } from './person-phone.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonPhoneService {

    private resourceUrl = 'api/person-phones';

    constructor(private http: Http) { }

    create(personPhone: PersonPhone): Observable<PersonPhone> {
        const copy = this.convert(personPhone);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(personPhone: PersonPhone): Observable<PersonPhone> {
        const copy = this.convert(personPhone);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PersonPhone> {
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

    private convert(personPhone: PersonPhone): PersonPhone {
        const copy: PersonPhone = Object.assign({}, personPhone);
        return copy;
    }
}
