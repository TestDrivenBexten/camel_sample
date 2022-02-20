import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.rest.RestBindingMode

data class Item(val name: String, val quality: Int)

typealias ItemMap = LinkedHashMap<String, Any>

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
            .outType(Array<Item>::class.java)
            .to("direct:item")

        from("direct:item")
            .process { exchange ->
                val rawItemList = exchange.message.body as List<ItemMap>
                val itemList = rawItemList.map { item ->
                    Item(
                        name = item["name"] as String,
                        quality = item["quality"] as Int,
                    )
                }
                exchange.message.body = itemList
            }
            .split(body())
                .choice()
                    .`when` { (it.message.body as Item).quality > 90 }
                        .to("direct:storage")
                    .otherwise().to("direct:trashbin")
                .end()

        from("direct:storage")
            .process {
                val item = it.message.body as Item
                println("Good quality item ${item.name} stored")
            }

        from("direct:trashbin")
            .process {
                val item = it.message.body as Item
                println("Low quality item ${item.name} tossed")
            }
    }

}