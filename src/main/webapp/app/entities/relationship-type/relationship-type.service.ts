import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RelationshipType } from './relationship-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RelationshipTypeService {

    private resourceUrl = 'api/relationship-types';

    constructor(private http: Http) { }

    create(relationshipType: RelationshipType): Observable<RelationshipType> {
        const copy = this.convert(relationshipType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(relationshipType: RelationshipType): Observable<RelationshipType> {
        const copy = this.convert(relationshipType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RelationshipType> {
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

    private convert(relationshipType: RelationshipType): RelationshipType {
        const copy: RelationshipType = Object.assign({}, relationshipType);
        return copy;
    }
}
