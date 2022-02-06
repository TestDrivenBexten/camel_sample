import org.apache.camel.builder.RouteBuilder

class MyRouteBuilder() : RouteBuilder() {

    override fun configure() {
        // from("timer:foo?repeatCount=1?")
        //     .setBody(constant("Hello, Camel!"))
        //     .to("stream:out")

        restConfiguration()
            .component("netty-http")
            .host("localhost").port(8080)

        from("rest:get:hello")
            .transform().constant("Bye World");

        from("rest:post:item")
            .process {
                println(it.message.body)
                println(it.message.headers)
            }
    }

}