import org.apache.camel.main.Main

/**
 * A static main() so we can easily run these routing rules in our IDE
 */
fun main(args: Array<String>) {
    println("\n\n\n\n");
    println("===============================================");
    println("Camel Samples");
    println("===============================================");
    println("\n\n\n\n");

    val main = Main()
    main.bind("itemDataSet", buildItemDataSet())

    main.configure().addRoutesBuilder(DataSetRouteBuilder())
    main.configure().addRoutesBuilder(ItemRouteBuilder())
    main.configure().addRoutesBuilder(ErrorRouteBuilder())
    main.configure().addRoutesBuilder(RestRouteBuilder())
    main.run(args)
}