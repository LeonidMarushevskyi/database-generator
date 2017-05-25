import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Complaint } from './complaint.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ComplaintService {

    private resourceUrl = 'api/complaints';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(complaint: Complaint): Observable<Complaint> {
        const copy = this.convert(complaint);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(complaint: Complaint): Observable<Complaint> {
        const copy = this.convert(complaint);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Complaint> {
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
        entity.complaintDate = this.dateUtils
            .convertLocalDateFromServer(entity.complaintDate);
        entity.approvalDate = this.dateUtils
            .convertLocalDateFromServer(entity.approvalDate);
    }

    private convert(complaint: Complaint): Complaint {
        const copy: Complaint = Object.assign({}, complaint);
        copy.complaintDate = this.dateUtils
            .convertLocalDateToServer(complaint.complaintDate);
        copy.approvalDate = this.dateUtils
            .convertLocalDateToServer(complaint.approvalDate);
        return copy;
    }
}
