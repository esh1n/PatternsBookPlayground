object ObserverExample {
    @JvmStatic
    fun main(args: Array<String>) {
        WeatherStation().run {
            listOf(CurrentConditionalDisplay(), VrnWeatherDisplay()).forEach(::registerObserver)
            setWeatherData { copy(temp = 80f, humidity = 65f, pressure = 30f) }
            setWeatherData { copy(temp = 75f, humidity = 65f, pressure = 50f) }
            setWeatherData { copy(temp = 70f, humidity = 65f, pressure = 80f) }
        }

    }

    interface Subject {
        fun registerObserver(o: Observer)
        fun removeObserver(o: Observer)
        fun notifyObservers()
    }

    interface Observer {
        fun update(data: WeatherData)
    }

    interface DisplayElement {
        fun display()
    }

    data class WeatherData(val temp: Float, val humidity: Float, val pressure: Float)

    class WeatherStation : Subject {
        private val observers = mutableListOf<Observer>()
        private var currentWeather: WeatherData = WeatherData(0f, 0f, 0f)

        override fun registerObserver(o: Observer) {
            observers.add(o)
        }

        override fun removeObserver(o: Observer) {
            observers.remove(o)
        }

        override fun notifyObservers() {
            observers.forEach { currentWeather.let(it::update) }
        }


        fun setWeatherData(transform: WeatherData.() -> WeatherData) {
            currentWeather = transform(currentWeather)
            notifyObservers()
        }
    }

    class CurrentConditionalDisplay : Observer, DisplayElement {
        private var innerWeatherData: WeatherData? = null
        override fun update(data: WeatherData) {
            innerWeatherData = data
            display()
        }

        override fun display() {
            innerWeatherData?.let {
                println(
                    "Current conditions: ${it.humidity} % humidity," +
                            "${it.temp} F temp,${it.pressure} % Pa pressure"
                )
            }
        }

    }

    class VrnWeatherDisplay : Observer, DisplayElement {
        private var innerWeatherData: WeatherData? = null
        override fun update(data: WeatherData) {
            innerWeatherData = data
            display()
        }

        override fun display() {
            innerWeatherData?.let {
                println(
                    "Voronezh Temperature:${it.temp} F"
                )
            }
        }

    }
}