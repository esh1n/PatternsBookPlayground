object DecoratorExample {
    @JvmStatic
    fun main(args: Array<String>) {
        val beverage = Espresso(Size.VENTI)
        println("${beverage.getDescription()} $ ${beverage.cost()}")

        var beverage2:Beverage = DarkRoast(Size.TALL)
        beverage2 = Whip(beverage2)
        println("${beverage2.getDescription()} $ ${beverage2.cost()}")

        val beverage3:Beverage = Whip(Mocha(Soy(beverage)))
        println("${beverage3.getDescription()} $ ${beverage3.cost()}")
    }

    enum class Size {
        SHORT,TALL,VENTI
    }

    abstract class Beverage(val size:Size = Size.TALL) {
        abstract fun cost(): Double
        abstract fun getDescription():String
    }

    abstract class CondimentDecorator : Beverage() {
    }

    class Espresso(size: Size) : Beverage(size) {
        override fun cost() = when(size){
            Size.SHORT -> 1.19
            Size.TALL -> 1.29
            Size.VENTI -> 1.32
        }
        override fun getDescription() ="Espresso Blonde ${size.name}"
    }

    class HouseBlend(size: Size) : Beverage(size) {
        override fun cost() = when(size){
            Size.SHORT -> 0.79
            Size.TALL -> 0.89
            Size.VENTI -> 0.932
        }
        override fun getDescription()="HouseBlend Coffee ${size.name}"
    }

    class DarkRoast(size: Size) : Beverage(size) {
        override fun cost() = when(size){
            Size.SHORT -> 0.59
            Size.TALL -> 0.69
            Size.VENTI -> 0.72
        }
        override fun getDescription() = "DarkRoast Coffee ${size.name}"
    }

    class Mocha(val beverage: Beverage) : CondimentDecorator() {
        override fun getDescription() = "${beverage.getDescription()}, Mocha"

        override fun cost() = beverage.cost() + costBySize()

        private fun costBySize() = when(beverage.size){
            Size.SHORT -> 0.10
            Size.TALL -> 0.20
            Size.VENTI -> 0.30
        }
    }
    class Whip(val beverage: Beverage) : CondimentDecorator() {
        override fun getDescription() = "${beverage.getDescription()}, Whip"

        override fun cost() = beverage.cost() + costBySize()

        private fun costBySize() = when(beverage.size){
            Size.SHORT -> 0.15
            else -> 0.25
        }
    }
    class Soy(val beverage: Beverage) : CondimentDecorator() {
        override fun getDescription() = "${beverage.getDescription()}, Soy"

        override fun cost() = beverage.cost() + costBySize()

        private fun costBySize() =  1.15
    }
}