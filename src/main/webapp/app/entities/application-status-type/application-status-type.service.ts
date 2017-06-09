import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ApplicationStatusType } from './application-status-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ApplicationStatusTypeService {

    private resourceUrl = 'api/application-status-types';

    constructor(private http: Http) { }

    create(applicationStatusType: ApplicationStatusType): Observable<ApplicationStatusType> {
        const copy = this.convert(applicationStatusType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(applicationStatusType: ApplicationStatusType): Observable<ApplicationStatusType> {
        const copy = this.convert(applicationStatusType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ApplicationStatusType> {
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

    private convert(applicationStatusType: ApplicationStatusType): ApplicationStatusType {
        const copy: ApplicationStatusType = Object.assign({}, applicationStatusType);
        return copy;
    }
}
