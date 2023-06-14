package com.e1i5.stackOverflow.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriCreator {
    public static URI createUri(String defaultUrl, long id) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{comment-id}")
                .buildAndExpand(id)
                .toUri();
    }
}
