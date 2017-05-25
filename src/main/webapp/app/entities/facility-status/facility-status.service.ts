import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FacilityStatus } from './facility-status.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FacilityStatusService {

    private resourceUrl = 'api/facility-statuses';

    constructor(private http: Http) { }

    create(facilityStatus: FacilityStatus): Observable<FacilityStatus> {
        const copy = this.convert(facilityStatus);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(facilityStatus: FacilityStatus): Observable<FacilityStatus> {
        const copy = this.convert(facilityStatus);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FacilityStatus> {
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

    private convert(facilityStatus: FacilityStatus): FacilityStatus {
        const copy: FacilityStatus = Object.assign({}, facilityStatus);
        return copy;
    }
}
