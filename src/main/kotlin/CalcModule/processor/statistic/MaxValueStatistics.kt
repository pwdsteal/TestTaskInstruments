package CalcModule.processor.statistic

import CalcModule.model.Instrument

/**
 * Считает максимальное значение по продукту
 */
class MaxValueStatistics : Statistic() {
    var maxValue = 0.0

    override val metricValue: Double get() = maxValue

    @Synchronized
    override fun acquire(instrument: Instrument) {
        if (maxValue < instrument.value) maxValue = instrument.value
    }

    override fun toString(): String {
        return "MaxValueStatistics(metricValue=$metricValue)"
    }
}