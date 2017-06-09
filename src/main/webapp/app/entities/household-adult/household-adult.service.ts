import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { HouseholdAdult } from './household-adult.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HouseholdAdultService {

    private resourceUrl = 'api/household-adults';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(householdAdult: HouseholdAdult): Observable<HouseholdAdult> {
        const copy = this.convert(householdAdult);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(householdAdult: HouseholdAdult): Observable<HouseholdAdult> {
        const copy = this.convert(householdAdult);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<HouseholdAdult> {
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

    private convert(householdAdult: HouseholdAdult): HouseholdAdult {
        const copy: HouseholdAdult = Object.assign({}, householdAdult);

        copy.createDateTime = this.dateUtils.toDate(householdAdult.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(householdAdult.updateDateTime);
        return copy;
    }
}
