import { Component } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { InputService } from "../services/input.service";
import { QueryData } from "../services/responses/querydata";

@Component({
	selector: "app-input",
	templateUrl: "./input.component.html",
	styleUrls: ["./input.component.scss"],
})
export class InputComponent {
	loading = false;
	queryData: QueryData = {
		totalHits: 0,
		keywordHits: {
			keywordHits: "",
			keyword: 0,
		},
	};

	constructor(private countService: InputService) {}

	inputForm = new FormGroup({
		inputString: new FormControl("", [Validators.required]),
	});

	getCount() {
		this.loading = true;
		this.countService
			.getCount(this.inputForm.controls.inputString.getRawValue() as string)
			.subscribe((response) => {
				this.loading = false;
				console.log(response);
				this.queryData = response;
			});
	}

	reset() {
		this.queryData = {
			totalHits: 0,
			keywordHits: {
				keywordHits: "",
				keyword: 0,
			},
		};
		this.inputForm.controls.inputString.reset();
	}
}
