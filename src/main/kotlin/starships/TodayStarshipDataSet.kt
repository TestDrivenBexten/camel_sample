package starships

import org.apache.camel.component.dataset.SimpleDataSet

fun buildTodayShipDataSet(): SimpleDataSet {
    val dataSet = SimpleDataSet()

    val ship1 = Starship("X3491", "Pegasus","Alpha Centauri")
    val ship2 = Starship("J6732", "Icarus","Alpha Centauri")

    dataSet.defaultBody = listOf(ship1, ship2)
    return dataSet
}