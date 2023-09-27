package com.project.capstonedesign.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sort {
    LIKE("like"), UPDATE_DATE("update_date");


    private final String searchName;

    public static Sort searchSortType(String searchName) {
        for (Sort sort : Sort.values()) {
            if (sort.getSearchName().equals(searchName)) {
                return sort;
            }
        }
        throw new IllegalArgumentException("검색할 수 없는 정렬 방식입니다.");
    }
}
