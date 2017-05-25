import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PersonAddress } from './person-address.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonAddressService {

    private resourceUrl = 'api/person-addresses';

    constructor(private http: Http) { }

    create(personAddress: PersonAddress): Observable<PersonAddress> {
        const copy = this.convert(personAddress);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(personAddress: PersonAddress): Observable<PersonAddress> {
        const copy = this.convert(personAddress);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PersonAddress> {
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

    private convert(personAddress: PersonAddress): PersonAddress {
        const copy: PersonAddress = Object.assign({}, personAddress);
        return copy;
    }
}
