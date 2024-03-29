import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { EthnicityType } from './ethnicity-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EthnicityTypeService {

    private resourceUrl = 'api/ethnicity-types';

    constructor(private http: Http) { }

    create(ethnicityType: EthnicityType): Observable<EthnicityType> {
        const copy = this.convert(ethnicityType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(ethnicityType: EthnicityType): Observable<EthnicityType> {
        const copy = this.convert(ethnicityType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<EthnicityType> {
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

    private convert(ethnicityType: EthnicityType): EthnicityType {
        const copy: EthnicityType = Object.assign({}, ethnicityType);
        return copy;
    }
}
