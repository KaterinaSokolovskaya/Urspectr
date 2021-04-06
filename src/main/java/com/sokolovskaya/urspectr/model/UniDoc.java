package com.sokolovskaya.urspectr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniDoc {

    private String infoBank;

    private Long numberInInfoBank;

    private Long backspinId;
}
