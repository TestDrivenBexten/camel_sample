package starships

import org.apache.camel.component.dataset.SimpleDataSet
import java.time.LocalDate

private val ship1 = Starship("X3491", "Pegasus","Alpha Centauri")
private val ship2 = Starship("J6732", "Icarus","Barnard's Star")

fun buildTodayShipDataSet(): SimpleDataSet {
    val dataSet = SimpleDataSet()

    dataSet.defaultBody = listOf(ship1, ship2)
    dataSet.size = 1
    return dataSet
}

fun buildYesterdayShipList(): List<StarshipRecord> {
    val shipRecord1 = StarshipRecord(
        starship = ship1,
        currentDate = LocalDate.of(2022, 2, 20),
        expirationDate = null
    )
    val shipRecord2 = StarshipRecord(
        starship = ship2.copy(location = "Wolf 369"),
        currentDate = LocalDate.of(2022, 2, 20),
        expirationDate = null
    )
    return listOf(shipRecord1, shipRecord2)
}