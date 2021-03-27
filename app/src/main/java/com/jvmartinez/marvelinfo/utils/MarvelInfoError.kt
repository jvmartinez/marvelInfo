package com.jvmartinez.marvelinfo.utils

enum class MarvelInfoError(var messageError: String, var codeError: Int) {

    ERROR_API_KEY("Missing API Key", 409) {
        override fun error() = Exception("Missing API Key")
    },
    ERROR_HASH("Missing Hash", 409) {
        override fun error() = Exception("Missing Hash")
    },
    ERROR_TIMESTAMP("Missing Timestamp", 409) {
        override fun error() = Exception("Missing Timestamp")
    },
    ERROR_REFERER("Invalid Referer", 401) {
        override fun error() = Exception("Invalid Referer")
    },
    ERROR_INVALID_HASH("Invalid Hash", 401) {
        override fun error() = Exception("Invalid Hash")
    },
    ERROR_NOT_ALLOWED("Method Not Allowed", 405) {
        override fun error() = Exception("Method Not Allowed")
    },
    ERROR_FORBIDDEN("Forbidden", 403) {
        override fun error() = Exception("Forbidden")
    },
    ERROR_GENERIC("fail", 0) {
        override fun error() = Exception("Error Generic")
    };

    companion object {
        fun showError(messageError: String, codeError: Int): MarvelInfoError {
            if (ERROR_API_KEY.codeError == codeError && ERROR_API_KEY.messageError == messageError) {
                return ERROR_API_KEY
            } else if (ERROR_HASH.codeError == codeError && ERROR_HASH.messageError == messageError) {
                return ERROR_HASH
            } else if (ERROR_TIMESTAMP.codeError == codeError && ERROR_TIMESTAMP.messageError == messageError) {
                return ERROR_TIMESTAMP
            } else if (ERROR_REFERER.codeError == codeError && ERROR_REFERER.messageError == messageError) {
                return ERROR_REFERER
            } else if (ERROR_INVALID_HASH.codeError == codeError && ERROR_INVALID_HASH.messageError == messageError) {
                return ERROR_INVALID_HASH
            } else if (ERROR_NOT_ALLOWED.codeError == codeError && ERROR_NOT_ALLOWED.messageError == messageError) {
                return ERROR_NOT_ALLOWED
            } else if (ERROR_FORBIDDEN.codeError == codeError && ERROR_FORBIDDEN.messageError == messageError) {
                return ERROR_FORBIDDEN
            } else {
                return ERROR_FORBIDDEN
            }
        }
    }

    abstract fun error(): Exception


}
