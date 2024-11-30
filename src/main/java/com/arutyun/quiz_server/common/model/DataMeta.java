package com.arutyun.quiz_server.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DataMeta<T> {
    private List<T> data;
    private Meta meta;
}
