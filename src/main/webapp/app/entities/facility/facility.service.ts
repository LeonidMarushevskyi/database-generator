import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Facility } from './facility.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class FacilityService {

    private resourceUrl = 'api/facilities';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(facility: Facility): Observable<Facility> {
        let copy: Facility = Object.assign({}, facility);
        copy.licenseEffectiveDate = this.dateUtils
            .convertLocalDateToServer(facility.licenseEffectiveDate);
        copy.originalApplicationRecievedDate = this.dateUtils
            .convertLocalDateToServer(facility.originalApplicationRecievedDate);
        copy.lastVisitDate = this.dateUtils
            .convertLocalDateToServer(facility.lastVisitDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(facility: Facility): Observable<Facility> {
        let copy: Facility = Object.assign({}, facility);
        copy.licenseEffectiveDate = this.dateUtils
            .convertLocalDateToServer(facility.licenseEffectiveDate);
        copy.originalApplicationRecievedDate = this.dateUtils
            .convertLocalDateToServer(facility.originalApplicationRecievedDate);
        copy.lastVisitDate = this.dateUtils
            .convertLocalDateToServer(facility.lastVisitDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Facility> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.licenseEffectiveDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.licenseEffectiveDate);
            jsonResponse.originalApplicationRecievedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.originalApplicationRecievedDate);
            jsonResponse.lastVisitDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.lastVisitDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].licenseEffectiveDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].licenseEffectiveDate);
            jsonResponse[i].originalApplicationRecievedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].originalApplicationRecievedDate);
            jsonResponse[i].lastVisitDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].lastVisitDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
