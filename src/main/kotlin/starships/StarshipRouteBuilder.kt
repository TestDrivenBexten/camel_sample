package starships

import org.apache.camel.builder.RouteBuilder

class StarshipRouteBuilder: RouteBuilder() {
    override fun configure() {
        /*
            1. Load today's ships
            2. Load matching ship records
            3. Check for updates
                a. If not updated, do nothing
                b. If updated, send update to database
         */
        context.registry.bind("todayShip", buildTodayShipDataSet())

        from("direct:updateShipTable")
            .to("dataset:todayShip")
    }
}