import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { FacilityChild } from './facility-child.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FacilityChildService {

    private resourceUrl = 'api/facility-children';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(facilityChild: FacilityChild): Observable<FacilityChild> {
        const copy = this.convert(facilityChild);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(facilityChild: FacilityChild): Observable<FacilityChild> {
        const copy = this.convert(facilityChild);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<FacilityChild> {
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
        entity.dateOfPlacement = this.dateUtils
            .convertLocalDateFromServer(entity.dateOfPlacement);
    }

    private convert(facilityChild: FacilityChild): FacilityChild {
        const copy: FacilityChild = Object.assign({}, facilityChild);
        copy.dateOfPlacement = this.dateUtils
            .convertLocalDateToServer(facilityChild.dateOfPlacement);
        return copy;
    }
}
