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
    main.configure().addRoutesBuilder(MyRouteBuilder())
    main.run(args)
}