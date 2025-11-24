package nl.pcstet.core

sealed interface Error {
    sealed interface Data : Error {
        enum class Network : Data {
            BAD_REQUEST,
            UNAUTHORIZED,
            FORBIDDEN,
            NOT_FOUND,
            REQUEST_TIMEOUT,
            TOO_MANY_REQUESTS,
            NO_INTERNET,
            PAYLOAD_TOO_LARGE,
            SERVER_ERROR,
            BAD_GATEWAY,
            SERIALIZATION,
            GENERIC_SERVER,
            GENERIC_CLIENT,
            UNKNOWN,
        }

        enum class Local : Data {
            UNKNOWN

        }
    }
}