package com.opalka.notification.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class EmailClass {

    @NotNull
    @Email
    private String to;
    @NotNull
    private String title;
    @NotNull
    private String content;
}
