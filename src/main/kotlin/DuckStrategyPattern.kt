object DuckStrategyPattern {
    @JvmStatic
    fun main(args: Array<String>) {
        val decoyDuck = DecoyDuck()

        decoyDuck.display()
        decoyDuck.swim()

        decoyDuck.performFly()

        decoyDuck.performQuack()

        decoyDuck.setFlyBehaviour(FlyLikeRocket())

        decoyDuck.setQuackBehaviour(QuackMat())


        val ducks  = listOf(decoyDuck,Selezen())
        ducks.forEach {
            it.display()
            it.performFly()
            it.performQuack()
        }
    }

    class DecoyDuck : Duck(){
        override fun display() {
            println( "Decoy duck display")

        }
    }

    class Selezen : Duck(flyBehaviour = FlyWithWings(), quackBehaviour = QuackSilent()){
        override fun display() {
            println( "Selezen duck display")
        }
    }

    abstract class Duck(private var flyBehaviour : FlyBehaviour?  = null,private var quackBehaviour : QuackBehaviour?  = null){

        abstract fun display()

        fun swim(){
            println("I'm swimiing All ducks can swim")
        }

        fun performFly(){
            flyBehaviour?.fly()
            if(flyBehaviour==null){
                println("Cannot fly")
            }
        }

        fun performQuack(){
            quackBehaviour?.quack()
            if(quackBehaviour==null){
                println("Cannot quack")
            }
        }

        fun setFlyBehaviour(flyBehaviour : FlyBehaviour){
            this.flyBehaviour = flyBehaviour
        }

        fun setQuackBehaviour(quackBehaviour : QuackBehaviour){
            this.quackBehaviour = quackBehaviour
        }
    }

    interface FlyBehaviour{
        fun fly()
    }

    class QuackSilent: QuackBehaviour{
        override fun quack() {
            println("...")
        }

    }

    class QuackMat: QuackBehaviour{
        override fun quack() {
            println("bleat")
        }
    }

    class FlyWithWings: FlyBehaviour{
        override fun fly() {
            println("just wings fly")
        }

    }

    class FlyLikeRocket: FlyBehaviour{
        override fun fly() {
            println("ROCKET FLY!")
        }

    }

    interface QuackBehaviour{
        fun quack()
    }
}