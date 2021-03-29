package com.sokolovskaya.urspectr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {

    private String infoBank;

    private Long numberInInfoBank;

    private Long segmentId;

    private Boolean checked;

    private Boolean actualized;
}
