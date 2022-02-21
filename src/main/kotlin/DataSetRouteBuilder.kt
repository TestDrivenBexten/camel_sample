import org.apache.camel.builder.RouteBuilder

class DataSetRouteBuilder : RouteBuilder() {
    override fun configure() {
        from("dataset:itemDataSet")
            .process {
                println(it.message.headers)
            }
            .to("stream:out")
    }
}