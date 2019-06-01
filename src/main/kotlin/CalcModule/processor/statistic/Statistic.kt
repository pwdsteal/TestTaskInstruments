package CalcModule.processor.statistic

import CalcModule.model.Instrument
import CalcModule.processor.Processor

abstract class Statistic() {
    abstract val metricValue: Double
    abstract fun acquire(value: Instrument)

}