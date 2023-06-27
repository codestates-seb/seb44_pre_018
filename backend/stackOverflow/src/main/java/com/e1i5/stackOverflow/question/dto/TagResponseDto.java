package com.e1i5.stackOverflow.question.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TagResponseDto {
    private String tagName;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TagResponseDto) {
            TagResponseDto tagResponseDtoNew = (TagResponseDto) obj;
            return tagResponseDtoNew.tagName.equals(tagName);
        } else {
            return false;
        }
    }

}