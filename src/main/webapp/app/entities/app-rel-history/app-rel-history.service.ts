import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { AppRelHistory } from './app-rel-history.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AppRelHistoryService {

    private resourceUrl = 'api/app-rel-histories';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(appRelHistory: AppRelHistory): Observable<AppRelHistory> {
        const copy = this.convert(appRelHistory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(appRelHistory: AppRelHistory): Observable<AppRelHistory> {
        const copy = this.convert(appRelHistory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<AppRelHistory> {
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
        entity.createDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.createDateTime);
        entity.updateDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.updateDateTime);
    }

    private convert(appRelHistory: AppRelHistory): AppRelHistory {
        const copy: AppRelHistory = Object.assign({}, appRelHistory);

        copy.createDateTime = this.dateUtils.toDate(appRelHistory.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(appRelHistory.updateDateTime);
        return copy;
    }
}
