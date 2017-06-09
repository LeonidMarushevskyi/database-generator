import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Employment } from './employment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EmploymentService {

    private resourceUrl = 'api/employments';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(employment: Employment): Observable<Employment> {
        const copy = this.convert(employment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(employment: Employment): Observable<Employment> {
        const copy = this.convert(employment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Employment> {
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
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(entity.endDate);
        entity.createDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.createDateTime);
        entity.updateDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.updateDateTime);
    }

    private convert(employment: Employment): Employment {
        const copy: Employment = Object.assign({}, employment);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(employment.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(employment.endDate);

        copy.createDateTime = this.dateUtils.toDate(employment.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(employment.updateDateTime);
        return copy;
    }
}
