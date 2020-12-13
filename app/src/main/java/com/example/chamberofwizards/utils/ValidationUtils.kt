package com.example.chamberofwizards.utils

import java.util.regex.Pattern

class ValidationUtils {
    companion object {
        val EMAIL_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        fun isEmailValid(email: String): Boolean {
            return EMAIL_PATTERN.matcher(email).matches()
        }
    }
}