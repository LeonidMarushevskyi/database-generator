import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FacilityAddress } from './facility-address.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FacilityAddressService {

    private resourceUrl = 'api/facility-addresses';

    constructor(private http: Http) { }

    create(facilityAddress: FacilityAddress): Observable<FacilityAddress> {
        const copy = this.convert(facilityAddress);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(facilityAddress: FacilityAddress): Observable<FacilityAddress> {
        const copy = this.convert(facilityAddress);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FacilityAddress> {
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

    private convert(facilityAddress: FacilityAddress): FacilityAddress {
        const copy: FacilityAddress = Object.assign({}, facilityAddress);
        return copy;
    }
}
