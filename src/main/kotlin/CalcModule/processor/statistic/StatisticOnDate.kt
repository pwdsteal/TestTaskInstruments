package CalcModule.processor.statistic

import CalcModule.model.Instrument
import java.time.LocalDate

/**
 * Простая статистика с фильтром по дате
 */
class StatisticOnDate(metric: Metric, val closedRange: ClosedRange<LocalDate>) : SimpleStatistic(metric) {
    override fun acquire(instrument: Instrument) {
        if (instrument.date in closedRange) {
            super.acquire(instrument)
        }
    }

    override fun toString(): String {
        return "StatisticOnDate(closedRange=$closedRange, ${super.toString()})"
    }
}