import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Facility } from './facility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FacilityService {

    private resourceUrl = 'api/facilities';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(facility: Facility): Observable<Facility> {
        const copy = this.convert(facility);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(facility: Facility): Observable<Facility> {
        const copy = this.convert(facility);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Facility> {
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
        entity.licenseEffectiveDate = this.dateUtils
            .convertLocalDateFromServer(entity.licenseEffectiveDate);
        entity.originalApplicationRecievedDate = this.dateUtils
            .convertLocalDateFromServer(entity.originalApplicationRecievedDate);
        entity.lastVisitDate = this.dateUtils
            .convertLocalDateFromServer(entity.lastVisitDate);
    }

    private convert(facility: Facility): Facility {
        const copy: Facility = Object.assign({}, facility);
        copy.licenseEffectiveDate = this.dateUtils
            .convertLocalDateToServer(facility.licenseEffectiveDate);
        copy.originalApplicationRecievedDate = this.dateUtils
            .convertLocalDateToServer(facility.originalApplicationRecievedDate);
        copy.lastVisitDate = this.dateUtils
            .convertLocalDateToServer(facility.lastVisitDate);
        return copy;
    }
}
