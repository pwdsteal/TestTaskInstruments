package CalcModule.processor.statistic

import CalcModule.model.Instrument
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

open class SimpleStatistic : Statistic() {
    private var count = 0
    private var valueSum = 0.0

    override val metricValue get() = valueSum / count

    @Synchronized
    override fun acquire(instrument: Instrument) {
        valueSum += instrument.value
        count++
    }

    override fun toString(): String {
        return "SimpleStatistic(metricValue=$metricValue)"
    }


}