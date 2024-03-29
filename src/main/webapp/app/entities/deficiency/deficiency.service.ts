import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Deficiency } from './deficiency.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DeficiencyService {

    private resourceUrl = 'api/deficiencies';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(deficiency: Deficiency): Observable<Deficiency> {
        const copy = this.convert(deficiency);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(deficiency: Deficiency): Observable<Deficiency> {
        const copy = this.convert(deficiency);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Deficiency> {
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
        entity.pocDate = this.dateUtils
            .convertDateTimeFromServer(entity.pocDate);
    }

    private convert(deficiency: Deficiency): Deficiency {
        const copy: Deficiency = Object.assign({}, deficiency);

        copy.pocDate = this.dateUtils.toDate(deficiency.pocDate);
        return copy;
    }
}
