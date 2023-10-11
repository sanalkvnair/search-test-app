import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, catchError, of, tap } from "rxjs";
import { QueryData } from "./responses/querydata";
@Injectable({
	providedIn: "root",
})
export class InputService {
	constructor(private httpClient: HttpClient) {}
	httpOptions = {
		headers: new HttpHeaders({ "Content-Type": "application/json" }),
	};

	getCount(queryString: string): Observable<QueryData> {
		const headers = new HttpHeaders();

		return this.httpClient
			.get<QueryData>(
				"http://localhost:8080/app/search?query=" + queryString,
				this.httpOptions
			)
			.pipe(
				tap((tc: QueryData) => console.log("Response", tc)),
				catchError(this.handleError<QueryData>("QueryData"))
			);
	}

	private handleError<T>(operation = "operation", result?: T) {
		return (error: any): Observable<T> => {
			console.error(error); // log to console instead
			this.log(`${operation} failed: ${error.message}`);
			return of(error);
		};
	}

	private log(message: string) {
		console.log(message);
	}
}
