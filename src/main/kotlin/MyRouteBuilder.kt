import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
            .outType(Array<Item>::class.java)
            .to("direct:item")

        from("direct:item")
            .process { exchange ->
                val rawItemList = exchange.message.body as List<LinkedHashMap<String, Any>>
                val itemList = rawItemList.map { item ->
                    Item(
                        name = item["name"] as String,
                        quality = item["quality"] as Int,
                    )
                }
//                val itemList = Gson().fromJson(rawJson, Array<Item>::class.java).toList()
                println(itemList)
            }
    }

}