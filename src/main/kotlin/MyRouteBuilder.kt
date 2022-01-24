import org.apache.camel.builder.RouteBuilder

class MyRouteBuilder() : RouteBuilder() {

    override fun configure() {
        from("timer:foo?repeatCount=1?")
            .setBody(constant("Hello, Camel!"))
            .to("stream:out")
    }

}