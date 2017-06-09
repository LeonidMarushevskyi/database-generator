import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { CriminalRecord } from './criminal-record.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CriminalRecordService {

    private resourceUrl = 'api/criminal-records';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(criminalRecord: CriminalRecord): Observable<CriminalRecord> {
        const copy = this.convert(criminalRecord);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(criminalRecord: CriminalRecord): Observable<CriminalRecord> {
        const copy = this.convert(criminalRecord);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CriminalRecord> {
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
        entity.offenseDate = this.dateUtils
            .convertLocalDateFromServer(entity.offenseDate);
        entity.createDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.createDateTime);
        entity.updateDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.updateDateTime);
    }

    private convert(criminalRecord: CriminalRecord): CriminalRecord {
        const copy: CriminalRecord = Object.assign({}, criminalRecord);
        copy.offenseDate = this.dateUtils
            .convertLocalDateToServer(criminalRecord.offenseDate);

        copy.createDateTime = this.dateUtils.toDate(criminalRecord.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(criminalRecord.updateDateTime);
        return copy;
    }
}
