package com.intuit.assignment.Validators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class EmailValidator {
    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmail(String s) {
        return EMAIL.matcher(s).matches();
    }
}
