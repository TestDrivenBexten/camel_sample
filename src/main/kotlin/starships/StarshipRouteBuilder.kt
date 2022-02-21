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

        from("dataset:todayShip")
            .process {
                println(it.message.body)
                val shipList = it.message.body as List<Starship>
                it.message.headers["TodayShipList"] = shipList
            }
            .process {
                println("Pretending we called a second data source")
                val shipRecordList = buildYesterdayShipList()
                println("Ship record list will be handled in next processor")
                it.message.body = shipRecordList
            }
            .process {
                val shipRecordList = it.message.body as List<StarshipRecord>
                it.message.headers["ShipRecordList"] = shipRecordList
            }
            .process {
                // Check for updates in this processor
                val todayShipList = it.message.headers["TodayShipList"] as List<Starship>
                val shipRecordList = it.message.headers["ShipRecordList"] as List<StarshipRecord>
                println(shipRecordList)
            }
    }
}

typealias ShipKey = Long
// Old record to expire and new record to create
typealias ShipUpdate = Pair<ShipKey, Starship>
data class ShipUpdateResult(
    val newShipList: List<Starship>,
    val updateShipList: List<ShipUpdate>
)

fun checkIfShipUpdated(ship1: Starship, ship2: Starship): Boolean {
    return ship1 != ship2
}