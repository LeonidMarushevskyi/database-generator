import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Inspection } from './inspection.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InspectionService {

    private resourceUrl = 'api/inspections';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(inspection: Inspection): Observable<Inspection> {
        const copy = this.convert(inspection);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(inspection: Inspection): Observable<Inspection> {
        const copy = this.convert(inspection);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Inspection> {
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
        entity.representativeSignatureDate = this.dateUtils
            .convertLocalDateFromServer(entity.representativeSignatureDate);
        entity.form809PrintDate = this.dateUtils
            .convertLocalDateFromServer(entity.form809PrintDate);
    }

    private convert(inspection: Inspection): Inspection {
        const copy: Inspection = Object.assign({}, inspection);
        copy.representativeSignatureDate = this.dateUtils
            .convertLocalDateToServer(inspection.representativeSignatureDate);
        copy.form809PrintDate = this.dateUtils
            .convertLocalDateToServer(inspection.form809PrintDate);
        return copy;
    }
}
