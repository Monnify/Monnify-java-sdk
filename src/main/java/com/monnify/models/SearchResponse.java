package com.monnify.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse<T> {
    private List<T> content;
    private Pageable pageable;
    private boolean last;
    private int totalPages;
    private int totalElements;
    private Sort sort;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;
    private boolean empty;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pageable {
        private Sort sort;
        private int pageSize;
        private int pageNumber;
        private int offset;
        private boolean paged;
        private boolean unpaged;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sort {
        private boolean sorted;
        private boolean unsorted;
        private boolean empty;
    }
}