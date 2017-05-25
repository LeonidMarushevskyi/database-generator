import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PersonRace } from './person-race.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonRaceService {

    private resourceUrl = 'api/person-races';

    constructor(private http: Http) { }

    create(personRace: PersonRace): Observable<PersonRace> {
        const copy = this.convert(personRace);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(personRace: PersonRace): Observable<PersonRace> {
        const copy = this.convert(personRace);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PersonRace> {
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

    private convert(personRace: PersonRace): PersonRace {
        const copy: PersonRace = Object.assign({}, personRace);
        return copy;
    }
}
