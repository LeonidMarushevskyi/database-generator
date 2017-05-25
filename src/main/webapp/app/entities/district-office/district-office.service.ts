import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DistrictOffice } from './district-office.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DistrictOfficeService {

    private resourceUrl = 'api/district-offices';

    constructor(private http: Http) { }

    create(districtOffice: DistrictOffice): Observable<DistrictOffice> {
        const copy = this.convert(districtOffice);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(districtOffice: DistrictOffice): Observable<DistrictOffice> {
        const copy = this.convert(districtOffice);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DistrictOffice> {
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

    private convert(districtOffice: DistrictOffice): DistrictOffice {
        const copy: DistrictOffice = Object.assign({}, districtOffice);
        return copy;
    }
}
