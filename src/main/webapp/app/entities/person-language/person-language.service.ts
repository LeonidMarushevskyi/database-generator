import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PersonLanguage } from './person-language.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonLanguageService {

    private resourceUrl = 'api/person-languages';

    constructor(private http: Http) { }

    create(personLanguage: PersonLanguage): Observable<PersonLanguage> {
        const copy = this.convert(personLanguage);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(personLanguage: PersonLanguage): Observable<PersonLanguage> {
        const copy = this.convert(personLanguage);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PersonLanguage> {
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

    private convert(personLanguage: PersonLanguage): PersonLanguage {
        const copy: PersonLanguage = Object.assign({}, personLanguage);
        return copy;
    }
}
