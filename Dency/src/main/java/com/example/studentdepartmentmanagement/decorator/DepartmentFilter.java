package com.example.studentdepartmentmanagement.decorator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentFilter {

    String Search;
    String Id;

    @JsonIgnore
    boolean softDelete = false;

    public String getSearch(){
        if(Search !=null){
            return Search.trim();
        }
        return Search;
    }
}
