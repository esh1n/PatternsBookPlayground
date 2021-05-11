object StrategyPatternExample {
    @JvmStatic
    fun main(args: Array<String>) {
        System.out.println("Strategy Pattern Example with Ducks")
        val ducksInSwimmingPool = arrayOf(MallardDuck(),ToyDuck())
        ducksInSwimmingPool.forEach { duck ->
            duck.display()
            duck.performFly()
            duck.performQuack()
        }
        val toyDuck = ToyDuck()
        toyDuck.setFlyBehavior(FlyRocketPowered())
        toyDuck.display()
        toyDuck.performFly()
        toyDuck.swim()
    }

    class MallardDuck():Duck(){

        init {
            flyBehaviour = FlyWithWings()
            quackBehaviour = Squeak()
        }
        override fun display() {
            System.out.println("I am real mallard duck")
        }

    }


    class ToyDuck():Duck(){

        init {
            flyBehaviour = NoFly()
            quackBehaviour = MuteQuack()
        }
        override fun display() {
            System.out.println("I am toy duck")
        }


    }

    abstract class Duck {
        protected lateinit var flyBehaviour:FlyBehavior
        protected lateinit var quackBehaviour:QuackBehavior
        abstract fun display()
        fun swim(){
            System.out.println("All ducks float, even decoys!")
        }
        fun performFly(){
            flyBehaviour.fly()
        }
        fun performQuack(){
            quackBehaviour.quack()
        }

        fun setFlyBehavior(flyBehavior: FlyBehavior){
            this.flyBehaviour = flyBehaviour
        }
        fun setQuackBehavior(){
            this.quackBehaviour = quackBehaviour
        }
    }

    class Quack:QuackBehavior{
        override fun quack() {
            System.out.println("quack")
        }

    }

    class MuteQuack:QuackBehavior{
        override fun quack() {
            System.out.println("--Silence--")
        }

    }

    class Squeak:QuackBehavior{
        override fun quack() {
            System.out.println("--squeak--")
        }
    }

    class FlyWithWings:FlyBehavior{
        override fun fly() {
            System.out.println("--fly with wings--")
        }
    }

    class FlyRocketPowered:FlyBehavior{
        override fun fly() {
            System.out.println("--Red Rocket!!!--")
        }
    }

    class NoFly:FlyBehavior{
        override fun fly() {
            System.out.println("--no fly--")
        }
    }

    interface FlyBehavior {
        fun fly()
    }

    interface QuackBehavior {
        fun quack()
    }
}
