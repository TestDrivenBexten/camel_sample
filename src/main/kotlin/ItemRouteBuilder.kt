import com.google.gson.Gson
import org.apache.camel.builder.DeadLetterChannelBuilder
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.rest.RestBindingMode

data class Item(val name: String, val quality: Int)

typealias CamelHeader = Pair<String, Any?>
data class CamelError(val body: Any, val headerList: List<CamelHeader>)

typealias ItemMap = LinkedHashMap<String, Any>

class ItemRouteBuilder : RouteBuilder() {

    override fun configure() {
        from("direct:parseItemList")
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
            .to("direct:item")

        from("file:sample_json")
            .convertBodyTo(String::class.java)
            .process {
                val rawJson = it.message.body as String
                val itemList = Gson().fromJson(rawJson, Array<Item>::class.java).toList()
                it.message.body = itemList
            }
            .to("direct:item")

        from("direct:item")
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