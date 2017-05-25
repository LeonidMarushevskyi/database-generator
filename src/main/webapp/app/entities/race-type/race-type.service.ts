import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RaceType } from './race-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RaceTypeService {

    private resourceUrl = 'api/race-types';

    constructor(private http: Http) { }

    create(raceType: RaceType): Observable<RaceType> {
        const copy = this.convert(raceType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(raceType: RaceType): Observable<RaceType> {
        const copy = this.convert(raceType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RaceType> {
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

    private convert(raceType: RaceType): RaceType {
        const copy: RaceType = Object.assign({}, raceType);
        return copy;
    }
}
