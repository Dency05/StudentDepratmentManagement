package com.example.studentdepartmentmanagement.decorator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    int page;
    int limit;
    @JsonIgnore
    public PageRequest getPageRequest(){
        return PageRequest.of(page,limit);
    }
}
