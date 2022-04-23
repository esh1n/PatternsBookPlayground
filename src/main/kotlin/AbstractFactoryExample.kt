import AbstractFactoryExample.Doug.*
import AbstractFactoryExample.Veggie.*
import AbstractFactoryExample.Cheese.*
import AbstractFactoryExample.Pepperoni.*
import AbstractFactoryExample.Sauce.*
import AbstractFactoryExample.Clams.*

object AbstractFactoryExample {
    @JvmStatic
    fun main(args: Array<String>) {

        listOf(
            PizzaStoreImpl("YE", ChicagoPizzaIngredientFactory()),
            PizzaStoreImpl("NY", NYPizzaIngridientFactory())
        ).forEach {
            it.orderPizza("veggie")
            it.orderPizza("cheese")
        }
    }


    interface IPizzaIngredientFactory {
        fun createDoug(): Doug
        fun createCheese(): Cheese
        fun createSauce(): Sauce
        fun createVeggies(): List<Veggie>
        fun createPepperoni(): Pepperoni
        fun createClams(): Clams
    }

    class NYPizzaIngridientFactory : IPizzaIngredientFactory {
        override fun createDoug(): Doug = ThinCrustDough

        override fun createCheese(): Cheese = ReggianoCheese

        override fun createSauce(): Sauce = MarinaraSauce

        override fun createVeggies(): List<Veggie> = listOf(Garlic, Onion, Mushroom, Pepper)

        override fun createPepperoni(): Pepperoni = SlicedPepperoni

        override fun createClams(): Clams = FreshClams

    }

    class ChicagoPizzaIngredientFactory : IPizzaIngredientFactory {
        override fun createDoug(): Doug = ThickCrustDough

        override fun createCheese(): Cheese = MozarellaCheese

        override fun createSauce(): Sauce = MarinaraSauce

        override fun createVeggies(): List<Veggie> = listOf(Garlic, Onion, Spinach, BlackOlives)

        override fun createPepperoni(): Pepperoni = SlicedPepperoni

        override fun createClams(): Clams = FrozenClams

    }

    sealed class Doug{
        object ThinCrustDough : Doug()
        object ThickCrustDough : Doug()
    }

    sealed class Cheese{
        object ReggianoCheese : Cheese()
        object MozarellaCheese : Cheese()
    }

    sealed class Sauce{
        object MarinaraSauce : Sauce()
    }

    sealed class Veggie{
        object Garlic : Veggie()
        object Onion : Veggie()
        object Mushroom : Veggie()
        object Pepper : Veggie()
        object Spinach : Veggie()
        object BlackOlives : Veggie()
    }

    sealed class Pepperoni{
        object SlicedPepperoni : Pepperoni()
    }

    sealed class Clams{
        object FreshClams : Clams()
        object FrozenClams : Clams()
    }

    interface IPizzaCook {
        fun prepare()
        fun bake()
        fun cut()
        fun box()
    }

    interface IPizzaStore {
        fun orderPizza(type: String): Pizza
    }

    class PizzaStoreImpl(
        private val storeName: String,
        private val ingredientFactory: IPizzaIngredientFactory
    ) : IPizzaStore {

        private fun createPizza(type: String): Pizza {
            return when (type) {
                "cheese" -> CheesePizza(storeName.plus(type), ingredientFactory)
                "clam" -> ClamPizza(storeName.plus(type), ingredientFactory)
                else -> StandardPizza()
            }
        }

        override fun orderPizza(type: String) = createPizza(type).apply {
            prepare()
            bake()
            cut()
            box()
        }

    }

    open class Pizza(
        val name: String,
        val dough: Doug,
        val sauce: Sauce,
        val veggies: List<Veggie> = emptyList(),
        val cheese: Cheese,
        val pepperoni: Pepperoni? = null,
        val clams: Clams? = null
    ) : IPizzaCook {
        override fun prepare() {
            println("!!!PREPARING $name")
            print("Tossing dough... $dough")
            print(" Adding sauce... $sauce")
            print(" Adding toppings...")
            print(" Put cheese...+$cheese")
            pepperoni?.let { " Put cheese...+$it" }
            clams?.let { " Put clams...+$it" }
            veggies.forEach { veggie -> println("cut  $veggie") }
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

    class CheesePizza(name: String, pizzaIngredientFactory: IPizzaIngredientFactory) : Pizza(
        name,
        dough = pizzaIngredientFactory.createDoug(),
        sauce = pizzaIngredientFactory.createSauce(),
        cheese = pizzaIngredientFactory.createCheese()
    )

    class ClamPizza(name: String, pizzaIngredientFactory: IPizzaIngredientFactory) : Pizza(
        name,
        dough = pizzaIngredientFactory.createDoug(),
        sauce = pizzaIngredientFactory.createSauce(),
        cheese = pizzaIngredientFactory.createCheese(),
        clams = pizzaIngredientFactory.createClams()
    )

    class StandardPizza : Pizza("standard", cheese = MozarellaCheese, dough = ThinCrustDough, sauce = MarinaraSauce)
}
