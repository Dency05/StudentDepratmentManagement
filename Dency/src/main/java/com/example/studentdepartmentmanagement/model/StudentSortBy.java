package com.example.studentdepartmentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public enum StudentSortBy {
    STUDENT_NAME("StudentName"),
    STUDENT_ID("id"),
    AGE("age");

    @JsonIgnore
    private String value;
    StudentSortBy(String value) {
        this.value = value;
    }
    public static List<Map<String,String>> toList(){
        return Arrays.stream(values()).map(StudentSortBy::toMap).collect(Collectors.toList());
    }

    Map<String,String> toMap() {
        Map<String,String> map=new HashMap<>();
        map.put("value",this.toString());
        return map;
    }


}
