import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AssignedWorker } from './assigned-worker.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AssignedWorkerService {

    private resourceUrl = 'api/assigned-workers';

    constructor(private http: Http) { }

    create(assignedWorker: AssignedWorker): Observable<AssignedWorker> {
        const copy = this.convert(assignedWorker);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(assignedWorker: AssignedWorker): Observable<AssignedWorker> {
        const copy = this.convert(assignedWorker);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AssignedWorker> {
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

    private convert(assignedWorker: AssignedWorker): AssignedWorker {
        const copy: AssignedWorker = Object.assign({}, assignedWorker);
        return copy;
    }
}
