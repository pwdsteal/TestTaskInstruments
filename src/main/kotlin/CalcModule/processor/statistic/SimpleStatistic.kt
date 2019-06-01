package CalcModule.processor.statistic

import CalcModule.model.Instrument
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

open class SimpleStatistic : Statistic() {
    private val lock = ReentrantLock()
    private var count = 0
    private var valueSum = 0.0

    override val metricValue get() = valueSum / count

    override fun acquire(instrument: Instrument) {
        lock.withLock {
            valueSum += instrument.value
            count++
        }
    }

    override fun toString(): String {
        return "SimpleStatistic(metricValue=$metricValue)"
    }


}