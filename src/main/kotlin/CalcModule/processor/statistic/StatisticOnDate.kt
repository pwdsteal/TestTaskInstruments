package CalcModule.processor.statistic

import CalcModule.model.Instrument
import java.time.LocalDate

class StatisticOnDate(val closedRange: ClosedRange<LocalDate>) : SimpleStatistic() {
    override fun acquire(instrument: Instrument) {
        if (instrument.date in closedRange) {
            super.acquire(instrument)
        }
    }

    override fun toString(): String {
        return "StatisticOnDate(closedRange=$closedRange, ${super.toString()})"
    }
}