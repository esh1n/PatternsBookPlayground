object StrategyPatternExample {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Strategy Pattern Example with Ducks")
        val ducksInSwimmingPool = arrayOf(MallardDuck(), ToyDuck())
        ducksInSwimmingPool.forEach { duck ->
            duck.run {
                display()
                performFly()
                performQuack()
            }
        }
        ToyDuck().run {
            flyBehaviour = FlyRocketPowered()
            display()
            performFly()
            swim()
        }

    }

    class MallardDuck : Duck(flyBehaviour = FlyWithWings(), quackBehaviour = Squeak()) {

        override fun display() {
            println("I am real mallard duck")
        }

    }


    class ToyDuck : Duck(flyBehaviour = NoFly(),quackBehaviour = MuteQuack()) {

        override fun display() {
            println("I am toy duck")
        }
    }

    abstract class Duck(var flyBehaviour: FlyBehavior, var quackBehaviour: QuackBehavior) {
        abstract fun display()

        fun swim() {
            println("All ducks float, even decoys!")
        }

        fun performFly() {
            flyBehaviour.fly()
        }

        fun performQuack() {
            quackBehaviour.quack()
        }
    }

    class Quack : QuackBehavior {
        override fun quack() {
            println("quack")
        }

    }

    class MuteQuack : QuackBehavior {
        override fun quack() {
            println("--Silence--")
        }

    }

    class Squeak : QuackBehavior {
        override fun quack() {
            println("--squeak--")
        }
    }

    class FlyWithWings : FlyBehavior {
        override fun fly() {
            println("--fly with wings--")
        }
    }

    class FlyRocketPowered : FlyBehavior {
        override fun fly() {
            println("--Red Rocket!!!--")
        }
    }

    class NoFly : FlyBehavior {
        override fun fly() {
            println("--no fly--")
        }
    }

    interface FlyBehavior {
        fun fly()
    }

    interface QuackBehavior {
        fun quack()
    }
}
