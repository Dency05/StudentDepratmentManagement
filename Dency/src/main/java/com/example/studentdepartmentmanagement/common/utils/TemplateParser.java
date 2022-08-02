package com.example.studentdepartmentmanagement.common.utils;


import com.samskivert.mustache.Mustache;

public class TemplateParser<T> {
    private final Mustache.Compiler mustache = Mustache.compiler().defaultValue("").escapeHTML(false);
    public String compileTemplate(String template, T data){
        return mustache.compile(template).execute(data);
    }
}
