package com.project.splitit.view;

import com.project.splitit.util.LongObfuscator;

public interface Response {

    default Long mask(final Long number) {
        return number != null ? LongObfuscator.INSTANCE.obfuscate(number) : null;
    }
}
