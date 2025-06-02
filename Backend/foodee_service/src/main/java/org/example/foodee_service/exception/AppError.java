package org.example.foodee_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AppError {
  SUCCESS(1000, "Success", HttpStatus.OK),
  UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),

  // User related errors
  USER_NOT_EXIST(1001, "User not found", HttpStatus.BAD_REQUEST),

  USERNAME_EXISTED(1002, "Username already exists", HttpStatus.BAD_REQUEST),
  USERNAME_REQUIRED(1003, "Username is required", HttpStatus.BAD_REQUEST),
  USERNAME_INVALID(1004, "Username must be at least {min} characters long", HttpStatus.BAD_REQUEST),

  PASSWORD_INVALID(1005, "Password must be at least {min} characters long", HttpStatus.BAD_REQUEST),
  PASSWORD_REQUIRED(1006, "Password is required", HttpStatus.BAD_REQUEST),

  FIRST_NAME_REQUIRED(1007, "First name is required", HttpStatus.BAD_REQUEST),
  LAST_NAME_REQUIRED(1008, "Last name is required", HttpStatus.BAD_REQUEST),

  EMAIL_REQUIRED(1009, "Email is required", HttpStatus.BAD_REQUEST),
  EMAIL_INVALID(1010, "Email must be in the format 'example@domain.com'.", HttpStatus.BAD_REQUEST),

  DOB_REQUIRED(1011, "Date of birth is required", HttpStatus.BAD_REQUEST),
  DOB_INVALID(1012, "You must be at least {min} years old", HttpStatus.BAD_REQUEST),

  USER_STATUS_INVALID(1013, "User status is invalid", HttpStatus.BAD_REQUEST),

  USER_ID_REQUIRED(1014, "User id is required for this operation", HttpStatus.BAD_REQUEST),

  USER_EMAIL_EXISTED(1015, "Email đã được đăng ký bằng tài khoản khác", HttpStatus.BAD_REQUEST),
  USER_PHONE_EXISTED(1016, "Phone number already exists", HttpStatus.BAD_REQUEST),
  INVALID_USER_STATUS(1017, "Invalid user status", HttpStatus.BAD_REQUEST),

  // Permission related errors
  PERMISSION_NOT_EXIST(1030, "Permission not found", HttpStatus.BAD_REQUEST),
  PERMISSION_NAME_EXISTED(1031, "Permission name already exists", HttpStatus.BAD_REQUEST),
  PERMISSION_NAME_REQUIRED(1032, "Permission name is required", HttpStatus.BAD_REQUEST),
  PERMISSION_ID_REQUIRED(1033, "Permission id is required for this operation", HttpStatus.BAD_REQUEST),

  // Role related errors
  ROLE_NOT_EXIST(1040, "Role not found", HttpStatus.BAD_REQUEST),
  ROLE_NAME_EXISTED(1041, "Role name already exists", HttpStatus.BAD_REQUEST),
  ROLE_NAME_REQUIRED(1042, "Role name is required", HttpStatus.BAD_REQUEST),
  ROLE_ID_REQUIRED(1043, "Role id is required for this operation", HttpStatus.BAD_REQUEST),

  // Authentication and authorization errors
  UNAUTHENTICATED(1040, "Unauthenticated", HttpStatus.UNAUTHORIZED),
  USERNAME_PASSWORD_NULL(1041, "Username or password is null", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(1042, "You do not have permission for this operation", HttpStatus.FORBIDDEN),

  //Section related errors
  SECTION_NOT_EXIST(2001, "Section not found", HttpStatus.BAD_REQUEST),
  SECTION_NAME_EXISTED(2002, "Section name already exists", HttpStatus.BAD_REQUEST),
  SECTION_NAME_REQUIRED(2003, "Section name is required", HttpStatus.BAD_REQUEST),
  SECTION_ID_REQUIRED(2004, "Section id is required for this operation", HttpStatus.BAD_REQUEST),

  // Article-related errors
  ARTICLE_NOT_EXIST(2001, "Article not found", HttpStatus.BAD_REQUEST),
  ARTICLE_TITLE_EXISTED(2002, "Article title already exists", HttpStatus.BAD_REQUEST),
  ARTICLE_TITLE_REQUIRED(2003, "Article title is required", HttpStatus.BAD_REQUEST),
  ARTICLE_ID_REQUIRED(2004, "Article id is required for this operation", HttpStatus.BAD_REQUEST),
  ARTICLE_SECTION_NAME_REQUIRED(2005, "Article section name is required", HttpStatus.BAD_REQUEST),
  ARTICLE_CONTENT_REQUIRED(2006, "Article content is required", HttpStatus.BAD_REQUEST),
  ARTICLE_SUMMARY_REQUIRED(2007, "Article summary is required", HttpStatus.BAD_REQUEST),
  ARTICLE_AUTHOR_REQUIRED(2008, "Article author is required", HttpStatus.BAD_REQUEST),

  //Order related errors
  ORDER_UPDATE_STATUS_INVALID(2009,"Trạng thái đơn hàng không hợp lệ! Vui lòng thử lại.",HttpStatus.BAD_REQUEST),

  //Query related errors
  PAGE_OR_SIZE_INVALID(3001, "Page or size is invalid", HttpStatus.BAD_REQUEST);


  private final int code;
  private final String message;
  private final HttpStatusCode statusCode;

  AppError(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }
}
