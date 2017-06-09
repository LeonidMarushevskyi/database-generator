import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CountyType } from './county-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CountyTypeService {

    private resourceUrl = 'api/county-types';

    constructor(private http: Http) { }

    create(countyType: CountyType): Observable<CountyType> {
        const copy = this.convert(countyType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(countyType: CountyType): Observable<CountyType> {
        const copy = this.convert(countyType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CountyType> {
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

    private convert(countyType: CountyType): CountyType {
        const copy: CountyType = Object.assign({}, countyType);
        return copy;
    }
}
