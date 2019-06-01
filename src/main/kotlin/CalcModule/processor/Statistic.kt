package CalcModule.processor

import CalcModule.model.Instrument
import CalcModule.processor.Processor

abstract class Statistic() {
    abstract val metricValue: Double
    abstract fun acquire(value: Instrument)

    override fun toString(): String {
        return "${javaClass.simpleName}(value=$metricValue)"
    }
}