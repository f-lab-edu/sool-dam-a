package com.flab.sooldama.global.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
@RequiredArgsConstructor
public class HealthCheckApi {

	@GetMapping(path = "")
	public ResponseEntity<Void> healthCheck() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
