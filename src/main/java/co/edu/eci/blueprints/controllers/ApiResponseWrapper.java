package co.edu.eci.blueprints.controllers;

public record ApiResponseWrapper<T>(
        int code,
        String message,
        T data
) {}
