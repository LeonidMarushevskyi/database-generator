import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FacilityPhone } from './facility-phone.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FacilityPhoneService {

    private resourceUrl = 'api/facility-phones';

    constructor(private http: Http) { }

    create(facilityPhone: FacilityPhone): Observable<FacilityPhone> {
        const copy = this.convert(facilityPhone);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(facilityPhone: FacilityPhone): Observable<FacilityPhone> {
        const copy = this.convert(facilityPhone);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FacilityPhone> {
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

    private convert(facilityPhone: FacilityPhone): FacilityPhone {
        const copy: FacilityPhone = Object.assign({}, facilityPhone);
        return copy;
    }
}
