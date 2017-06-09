import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { HouseholdAddress } from './household-address.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HouseholdAddressService {

    private resourceUrl = 'api/household-addresses';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(householdAddress: HouseholdAddress): Observable<HouseholdAddress> {
        const copy = this.convert(householdAddress);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(householdAddress: HouseholdAddress): Observable<HouseholdAddress> {
        const copy = this.convert(householdAddress);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<HouseholdAddress> {
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

    private convert(householdAddress: HouseholdAddress): HouseholdAddress {
        const copy: HouseholdAddress = Object.assign({}, householdAddress);

        copy.createDateTime = this.dateUtils.toDate(householdAddress.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(householdAddress.updateDateTime);
        return copy;
    }
}
