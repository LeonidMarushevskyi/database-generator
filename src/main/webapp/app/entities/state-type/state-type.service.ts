import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { StateType } from './state-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StateTypeService {

    private resourceUrl = 'api/state-types';

    constructor(private http: Http) { }

    create(stateType: StateType): Observable<StateType> {
        const copy = this.convert(stateType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(stateType: StateType): Observable<StateType> {
        const copy = this.convert(stateType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<StateType> {
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

    private convert(stateType: StateType): StateType {
        const copy: StateType = Object.assign({}, stateType);
        return copy;
    }
}
