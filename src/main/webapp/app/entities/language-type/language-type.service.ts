import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { LanguageType } from './language-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LanguageTypeService {

    private resourceUrl = 'api/language-types';

    constructor(private http: Http) { }

    create(languageType: LanguageType): Observable<LanguageType> {
        const copy = this.convert(languageType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(languageType: LanguageType): Observable<LanguageType> {
        const copy = this.convert(languageType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<LanguageType> {
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

    private convert(languageType: LanguageType): LanguageType {
        const copy: LanguageType = Object.assign({}, languageType);
        return copy;
    }
}
