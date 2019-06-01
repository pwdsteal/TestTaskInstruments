package CalcModule.processor

import CalcModule.model.Instrument
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SimpleStatistic : Statistic() {
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

}