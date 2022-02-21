import org.apache.camel.component.dataset.SimpleDataSet

class ItemDataSet : SimpleDataSet() {
    override fun setDefaultBody(defaultBody: Any?) {
        super.setDefaultBody("This is a test")
        super.setSize(1)

        val itemKeyMap = mapOf(
            1 to "Apple",
            2 to "Banana",
            57 to "Iron Ore",
            123 to "Whale Bone",
        )
        val headers = mapOf(
            "itemKeyMap" to itemKeyMap
        )
        super.setDefaultHeaders(headers)
    }
}

fun buildItemDataSet(): SimpleDataSet {
    val itemDataSet = ItemDataSet()
    itemDataSet.defaultBody = "This is a test"
    itemDataSet.size = 1

    val itemKeyMap = mapOf(
        1 to "Apple",
        2 to "Banana",
        57 to "Iron Ore",
        123 to "Whale Bone",
    )
    val headers = mapOf(
        "itemKeyMap" to itemKeyMap
    )
    itemDataSet.defaultHeaders = headers
    return itemDataSet
}