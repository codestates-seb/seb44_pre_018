package com.e1i5.stackOverflow.member.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

<<<<<<< HEAD

=======
>>>>>>> 952e5b466be848b163551b59bb9c3e30081c5e24
@Getter
public class MultiResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;

    public MultiResponseDto(List<T> data, Page page) {
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() + 1,
                page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
