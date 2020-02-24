package com.maiboroda.easyWords.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.web.servlet.ResultMatcher;

import com.jayway.jsonpath.JsonPath;

public class ResponseBodyMatcher {
    private final static String MESSAGE_SEPARATOR = ", ";

    public ResultMatcher containsErrors(String[] expectedMessages) {
        return mvcResult -> {
            String messageString = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.message");
            String[] actualMessages = messageString.split(MESSAGE_SEPARATOR);

            assertThat(actualMessages).containsExactlyInAnyOrder(expectedMessages);
        };
    }

    public ResultMatcher containsError(String expectedMessage) {
        return mvcResult -> {
            String actualMessage = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.message");

            assertThat(actualMessage).isEqualTo(expectedMessage);
        };
    }

    public static ResponseBodyMatcher responseBody() {
        return new ResponseBodyMatcher();
    }
}
