package com.demo.dts_dev_challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public record CustomErrorResponse(HttpStatus status, String message) {}
