package com.e1i5.stackOverflow.question.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TagDto {

    @NotBlank
    private String tagName;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TagDto) {
            TagDto tagDtoNew = (TagDto) obj;
            return tagDtoNew.tagName.equals(tagName);
        } else {
            return false;
        }
    }


}