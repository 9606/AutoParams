package com.neu.autoparams.mvc.filter;

import org.springframework.security.core.AuthenticationException;

public class UserTelNotFoundException extends AuthenticationException {
    public UserTelNotFoundException(String msg) {
        super(msg);
    }

    /** @deprecated */
    @Deprecated
    public UserTelNotFoundException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }

    public UserTelNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}

