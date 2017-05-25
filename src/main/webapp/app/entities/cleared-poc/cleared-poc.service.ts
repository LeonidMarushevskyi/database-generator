import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { ClearedPOC } from './cleared-poc.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClearedPOCService {

    private resourceUrl = 'api/cleared-pocs';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(clearedPOC: ClearedPOC): Observable<ClearedPOC> {
        const copy = this.convert(clearedPOC);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(clearedPOC: ClearedPOC): Observable<ClearedPOC> {
        const copy = this.convert(clearedPOC);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ClearedPOC> {
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
        entity.pocduedate = this.dateUtils
            .convertDateTimeFromServer(entity.pocduedate);
        entity.pocdatecleared = this.dateUtils
            .convertDateTimeFromServer(entity.pocdatecleared);
    }

    private convert(clearedPOC: ClearedPOC): ClearedPOC {
        const copy: ClearedPOC = Object.assign({}, clearedPOC);

        copy.pocduedate = this.dateUtils.toDate(clearedPOC.pocduedate);

        copy.pocdatecleared = this.dateUtils.toDate(clearedPOC.pocdatecleared);
        return copy;
    }
}
