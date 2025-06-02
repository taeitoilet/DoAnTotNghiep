# Exception Handling Guide

## Overview

The application uses a global exception handling mechanism to provide consistent and detailed error responses. This guide describes how different types of exceptions are handled and the format of the error responses.

## Error Response Format

All error responses follow this general structure:

```json
{
  "status": 400,
  "code": "400",
  "message": "Validation failed",
  "traceId": "b2052f68-6c6c-4b18-824f-e78f35d839e0",
  "errors": [
    {
      "errorCode": "NotBlank",
      "errorDesc": "Role name cannot be blank",
      "refVal": "name"
    }
  ],
  "data": null,
  "timestamp": "2025-04-03T13:20:33.5264416"
}
```

Where:
- `status`: HTTP status code as an integer
- `code`: Status code as a string
- `message`: General error message
- `traceId`: Unique identifier for the request (useful for troubleshooting)
- `errors`: Array of specific error details (may be null for general errors)
- `data`: Response payload (null for error responses)
- `timestamp`: Time when the error occurred

## Types of Exceptions Handled

### Validation Exceptions

#### Request Body Validation (`MethodArgumentNotValidException`)

Triggered when `@Valid` annotation is used on request body objects and validation fails.

Example response:
```json
{
  "status": 400,
  "code": "400",
  "message": "Validation failed",
  "traceId": "b2052f68-6c6c-4b18-824f-e78f35d839e0",
  "errors": [
    {
      "errorCode": "NotBlank",
      "errorDesc": "Role name cannot be blank",
      "refVal": "name"
    },
    {
      "errorCode": "Pattern",
      "errorDesc": "Role name must contain only uppercase letters and underscores",
      "refVal": "name"
    }
  ],
  "data": null,
  "timestamp": "2025-04-03T13:20:33.5264416"
}
```

#### Request Parameter Validation (`ConstraintViolationException`)

Triggered when validation fails on request parameters (e.g., `@RequestParam`, `@PathVariable`) with annotations like `@NotBlank`, `@Pattern`.

Example response:
```json
{
  "status": 400,
  "code": "400",
  "message": "Validation failed",
  "traceId": "b2052f68-6c6c-4b18-824f-e78f35d839e0",
  "errors": [
    {
      "errorCode": "NotBlank",
      "errorDesc": "Permission name cannot be blank",
      "refVal": "name"
    }
  ],
  "data": null,
  "timestamp": "2025-04-03T13:20:33.5264416"
}
```

#### Type Mismatch (`MethodArgumentTypeMismatchException`)

Triggered when a parameter type does not match the expected type.

Example response:
```json
{
  "status": 400,
  "code": "400",
  "message": "Invalid parameter type",
  "traceId": "b2052f68-6c6c-4b18-824f-e78f35d839e0",
  "errors": [
    {
      "errorCode": "type.mismatch",
      "errorDesc": "Parameter 'pageNum' should be of type Integer",
      "refVal": "abc"
    }
  ],
  "data": null,
  "timestamp": "2025-04-03T13:20:33.5264416"
}
```

### Business Logic Exceptions

#### Resource Not Found (`ResourceNotFoundException`)

Triggered when a requested resource does not exist.

Example response:
```json
{
  "status": 404,
  "code": "404",
  "message": "Permission with name 'UNKNOWN_PERMISSION' not found",
  "traceId": "b2052f68-6c6c-4b18-824f-e78f35d839e0",
  "errors": null,
  "data": null,
  "timestamp": "2025-04-03T13:20:33.5264416"
}
```

#### Resource Already Exists (`ResourceAlreadyExistsException`)

Triggered when attempting to create a resource that already exists.

Example response:
```json
{
  "status": 409,
  "code": "409",
  "message": "Permission with name 'VIEW_USER' already exists",
  "traceId": "b2052f68-6c6c-4b18-824f-e78f35d839e0",
  "errors": null,
  "data": null,
  "timestamp": "2025-04-03T13:20:33.5264416"
}
```

### System Exceptions

#### Unknown Endpoint (`NoHandlerFoundException`)

Triggered when a request is made to an endpoint that doesn't exist.

Example response:
```json
{
  "status": 404,
  "code": "404",
  "message": "Endpoint not found",
  "traceId": "b2052f68-6c6c-4b18-824f-e78f35d839e0",
  "errors": null,
  "data": null,
  "timestamp": "2025-04-03T13:20:33.5264416"
}
```

#### General Server Error (`Exception`)

Catch-all for any unhandled exceptions.

Example response:
```json
{
  "status": 500,
  "code": "500",
  "message": "An unexpected error occurred",
  "traceId": "b2052f68-6c6c-4b18-824f-e78f35d839e0",
  "errors": null,
  "data": null,
  "timestamp": "2025-04-03T13:20:33.5264416"
}
```

## How to Use Validation

### Request Body Validation

Add validation annotations to DTO fields and use `@Valid` annotation in the controller method:

```java
@Data
public class RoleCreateRequest {
    @NotBlank(message = "Role name cannot be blank")
    @Pattern(regexp = "^[A-Z_]+$", message = "Role name must contain only uppercase letters and underscores")
    private String name;
}

@PostMapping("/create")
public ResponseEntity<ResultObject<RoleResponseDto>> createRole(
        @Valid @RequestBody RoleCreateRequest request) {
    // Implementation
}
```

### Request Parameter Validation

Add validation annotations to method parameters and add `@Validated` to the controller class:

```java
@RestController
@Validated
public class RoleController {
    @PostMapping("/detail")
    public ResponseEntity<ResultObject<RoleResponseDto>> getRoleDetail(
            @RequestParam @NotBlank(message = "Role name cannot be blank") 
            @Pattern(regexp = "^[A-Z_]+$", message = "Role name must contain only uppercase letters and underscores") 
            String name) {
        // Implementation
    }
}
``` 