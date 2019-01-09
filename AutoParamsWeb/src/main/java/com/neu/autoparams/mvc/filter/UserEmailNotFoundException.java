package com.neu.autoparams.mvc.filter;

import org.springframework.security.core.AuthenticationException;

public class UserEmailNotFoundException extends AuthenticationException {

    public UserEmailNotFoundException(String msg) {
        super(msg);
    }

    public UserEmailNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
