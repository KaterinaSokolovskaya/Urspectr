package com.sokolovskaya.urspectr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Backspin {

    private Long id;

    private String type;

    private String name;

    private List<String> generalTexts;

    private List<String> hiddenTexts;

    private List<String> generalTextsExactMatch;

    private List<String> hiddenTextsExactMatch;

    private List<String> teams;

    private Integer score;

    private List<Document> documents;

    private List<String> categories;

    private String userName;

    private String editionDate;

    private Boolean deleted;

    private Integer version;
}
