package com.example.studentdepartmentmanagement.common.utils;

import com.example.studentdepartmentmanagement.service.CustomAggregationOperation;
import org.bson.Document;

import java.util.List;
import java.util.Map;

public class AggregationUtils {
    public static CustomAggregationOperation lookup(String from, Map<String,Object> let, List<Document> pipeline, String asField) {
        return new CustomAggregationOperation(
                new Document(
                        "$lookup",
                        new Document("from", from)
                                .append("let", new Document(let))
                                .append("pipeline", pipeline)
                                .append("as", asField)
                )
        );
    }
}
