import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SiblingGroupType } from './sibling-group-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SiblingGroupTypeService {

    private resourceUrl = 'api/sibling-group-types';

    constructor(private http: Http) { }

    create(siblingGroupType: SiblingGroupType): Observable<SiblingGroupType> {
        const copy = this.convert(siblingGroupType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(siblingGroupType: SiblingGroupType): Observable<SiblingGroupType> {
        const copy = this.convert(siblingGroupType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<SiblingGroupType> {
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

    private convert(siblingGroupType: SiblingGroupType): SiblingGroupType {
        const copy: SiblingGroupType = Object.assign({}, siblingGroupType);
        return copy;
    }
}
