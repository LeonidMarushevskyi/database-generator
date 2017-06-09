import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AgeGroupType } from './age-group-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AgeGroupTypeService {

    private resourceUrl = 'api/age-group-types';

    constructor(private http: Http) { }

    create(ageGroupType: AgeGroupType): Observable<AgeGroupType> {
        const copy = this.convert(ageGroupType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(ageGroupType: AgeGroupType): Observable<AgeGroupType> {
        const copy = this.convert(ageGroupType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AgeGroupType> {
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

    private convert(ageGroupType: AgeGroupType): AgeGroupType {
        const copy: AgeGroupType = Object.assign({}, ageGroupType);
        return copy;
    }
}
