package dio.ssf_application.handler.errors

class InsufficientInformationException(s: String?) : IllegalArgumentException("$s is required") {
}