import org.apache.camel.builder.DeadLetterChannelBuilder
import org.apache.camel.builder.RouteBuilder

val ERROR_HANDLER = DeadLetterChannelBuilder("direct:error")
    .onExceptionOccurred {
        val body = it.message.body
        val headerList = it.message.headers.toList()
        it.message.body = CamelError(body, headerList)
    }

class ErrorRouteBuilder : RouteBuilder() {
    override fun configure() {
        from("direct:error")
            .process {
                val camelError = it.message.body as CamelError
                System.err.println("Error received $camelError")
                it.message.body = "Something went wrong"
            }

        from("direct:throwError")
            .errorHandler(ERROR_HANDLER)
            .process {
                throw Exception("Hello!")
            }
    }
}