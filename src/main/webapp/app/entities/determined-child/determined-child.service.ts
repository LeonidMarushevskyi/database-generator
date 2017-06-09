import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { DeterminedChild } from './determined-child.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DeterminedChildService {

    private resourceUrl = 'api/determined-children';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(determinedChild: DeterminedChild): Observable<DeterminedChild> {
        const copy = this.convert(determinedChild);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(determinedChild: DeterminedChild): Observable<DeterminedChild> {
        const copy = this.convert(determinedChild);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<DeterminedChild> {
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

    private convert(determinedChild: DeterminedChild): DeterminedChild {
        const copy: DeterminedChild = Object.assign({}, determinedChild);
        copy.dateOfPlacement = this.dateUtils
            .convertLocalDateToServer(determinedChild.dateOfPlacement);
        return copy;
    }
}
