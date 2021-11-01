object FactoryMethodExample {
    @JvmStatic
    fun main(args: Array<String>) {
        val firstChicagoStore = PizzaStoreImpl(ChicagoPizzaMaker())
        val secondNYPizzaStore = PizzaStoreImpl(NyPizzaMaker())
        listOf(firstChicagoStore,secondNYPizzaStore).forEach {
            it.orderPizza("veggie")
            it.orderPizza("cheese")
        }
    }

    interface IPizzaCook {
        fun prepare()
        fun bake()
        fun cut()
        fun box()
    }

    interface IPizzaMaker {
        fun createPizza(type: String): Pizza
    }

    class NyPizzaMaker : IPizzaMaker {
        override fun createPizza(type: String): Pizza {
            return when (type) {
                "cheese" -> NYStyleCheesePizza()
                "veggie" -> NYStyleVeggiePizza()
                else -> StandardPizza()
            }
        }
    }

    class ChicagoPizzaMaker : IPizzaMaker {
        override fun createPizza(type: String): Pizza {
            return when (type) {
                "cheese" -> ChicagoStyleCheesePizza()
                "veggie" -> ChicagoVeggieStyleCheesePizza()
                else -> StandardPizza()
            }
        }
    }

    interface IPizzaStore {
        fun orderPizza(type: String): Pizza
    }

    class PizzaStoreImpl(private val pizzaMaker: IPizzaMaker) : IPizzaStore {
        override fun orderPizza(type: String) = pizzaMaker.createPizza(type).apply {
            prepare()
            bake()
            cut()
            box()
        }

    }


    open class Pizza(val name: String, val dough: String, val sauce: String, val toppings: List<String>) : IPizzaCook {
        override fun prepare() {
            println("!!!PREPARING $name")
            println("Tossing dough... $dough")
            println("Adding sauce... $sauce")
            println("Adding toppings...")
            toppings.forEach { top -> println("  $top") }
            println("!!!FINISHED $name")

        }

        override fun bake() {
            println("Bake for 25 mins at 350")
        }

        override fun cut() {
            println("Cutting the pizza into diagonal slices")
        }

        override fun box() {
            println("Place pizza in official PizzaStore box")
        }
    }

    class StandardPizza : Pizza(
        name = "Standard Pizza", dough = "medium Crust Dough", sauce = "Russian Sauce",
        listOf("Stefan Durr Cheese")
    )

    class NYStyleCheesePizza : Pizza(
        name = "NY Style Sauce and Cheese Pizza", dough = "Thin Crust Dough", sauce = "Marinara Sauce",
        listOf("Grated Reggiano Cheese")
    )

    class NYStyleVeggiePizza : Pizza(
        name = "NY Style Veggie Pizza", dough = "Thin Crust Dough", sauce = "Marinara Sauce",
        listOf("Grated Reggiano Cheese")
    )

    class ChicagoVeggieStyleCheesePizza : Pizza(
        name = "Chicago Style Veggie and Cheese Pizza", dough = "Thin Crust Dough", sauce = "Marinara Sauce",
        listOf("Grated Reggiano Cheese")
    )

    class ChicagoStyleCheesePizza : Pizza(
        name = "Chicago Style Deep Dish Cheese Pizza", dough = "Extra Thick Crust Dough", sauce = "Plum Tomato Sauce",
        listOf("Shredded Mozzarella Cheese")
    ) {
        override fun cut() {
            println("Cutting the pizza into square slices")
        }
    }
}