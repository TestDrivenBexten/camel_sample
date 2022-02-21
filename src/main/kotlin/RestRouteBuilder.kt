import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.rest.RestBindingMode

class RestRouteBuilder : RouteBuilder() {
    override fun configure() {
        restConfiguration()
            .component("netty-http")
            .host("localhost")
            .port(8080)
            .bindingMode(RestBindingMode.json)

        from("rest:get:hello")
            .transform().constant("Bye World")

        rest("/items/")
            .post()
            .outType(String::class.java)
            .to("direct:parseItemList")

        rest("/throwError/")
            .post()
            .outType(String::class.java)
            .to("direct:throwError")
    }
}