package CalcModule.processor.statistic

import CalcModule.model.Instrument
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Простая статистика (Среднее, Сумма)
 */
open class SimpleStatistic(val metric: Metric) : Statistic() {
    private var count = 0
    private var valueSum = 0.0

    override val metricValue
        get() = when (metric) {
            Metric.MEAN -> valueSum / count
            Metric.SUM -> valueSum
        }

    @Synchronized
    override fun acquire(instrument: Instrument) {
        valueSum += instrument.value
        count++
    }

    override fun toString(): String {
        return "SimpleStatistic(metricValue=$metricValue)"
    }


}