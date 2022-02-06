import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.rest.RestBindingMode

data class Item(val name: String, val quality: Int)

class MyRouteBuilder() : RouteBuilder() {

    override fun configure() {
        // from("timer:foo?repeatCount=1?")
        //     .setBody(constant("Hello, Camel!"))
        //     .to("stream:out")

        restConfiguration()
            .component("netty-http")
            .host("localhost")
            .port(8080)
            .bindingMode(RestBindingMode.json)

        from("rest:get:hello")
            .transform().constant("Bye World");

        rest("/items/")
            .post()
            .outType(Class.forName("Item"))
            .to("stream:out")
    }

}