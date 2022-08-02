package com.example.studentdepartmentmanagement.decorator;

import com.example.studentdepartmentmanagement.constant.ResponseConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class Response {
    HttpStatus httpStatus ;
    String status;
    String description;

    public Response(HttpStatus ok, String ok1, String okDescription) {
        this.httpStatus =ok;
        this.status=ok1;
        this.description=okDescription;
    }

    public static Response getOkResponse(){
        return new Response(HttpStatus.OK, ResponseConstant.OK, ResponseConstant.ID_MATCHED);
    }

    public static Response getSuccessResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.SUCCESS, ResponseConstant.DEPARTMENT_FOUND);
    }

    public static Response getSuccessfullyResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.SUCCESS, ResponseConstant.INSERTED_SUCCESSFULLY);
    }

    public static Response getResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.SUCCESS, ResponseConstant.STUDENT_FOUND);
    }

    public static Response getDeletedid() {
        return new Response(HttpStatus.OK, ResponseConstant.OK, ResponseConstant.STUDENT_DELETE);
    }

    public static Response getNotFoundResponse(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.NOT_FOUND,message);
    }

    public static Response getAlreadyExitException(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.ALREADY_EXITS,message);
    }
    public static Response getDeleteid() {
        return new Response(HttpStatus.OK, ResponseConstant.OK, ResponseConstant.DEPARTMENT_DELETE);
    }

    public static Response getEmptyException(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.DEPARTMENT_EMPTY,message);
    }

    public static Response getEmptyStudentException(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.STUDENT_EMPTY,message);
    }

    public static Response sorting() {
        return new Response(HttpStatus.OK,ResponseConstant.SUCCESS,ResponseConstant.OK);
    }

    public static Response getOhkResponse(){
        return new Response(HttpStatus.OK, ResponseConstant.OK, ResponseConstant.OK);
    }
}
