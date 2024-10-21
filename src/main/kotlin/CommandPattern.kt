object CommandPattern {
    @JvmStatic
    fun main(args: Array<String>) {

    }

    interface Command {
        fun execute()
    }

    class LightOnCommand(private val light: Light) : Command {

        override fun execute() = light.lightOn()
    }

    class Light {
        fun lightOn() {
            println("light on")
        }

        fun lightOff() {
            println("light off")
        }
    }
}