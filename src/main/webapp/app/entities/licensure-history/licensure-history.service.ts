import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { LicensureHistory } from './licensure-history.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LicensureHistoryService {

    private resourceUrl = 'api/licensure-histories';

    constructor(private http: Http) { }

    create(licensureHistory: LicensureHistory): Observable<LicensureHistory> {
        const copy = this.convert(licensureHistory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(licensureHistory: LicensureHistory): Observable<LicensureHistory> {
        const copy = this.convert(licensureHistory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<LicensureHistory> {
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

    private convert(licensureHistory: LicensureHistory): LicensureHistory {
        const copy: LicensureHistory = Object.assign({}, licensureHistory);
        return copy;
    }
}
