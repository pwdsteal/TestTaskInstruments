package CalcModule.processor.statistic

import CalcModule.model.Instrument
import com.google.common.collect.MinMaxPriorityQueue
import java.util.*
import java.util.Comparator.comparing
import kotlin.Comparator

class SumOfNewest(
    val maxEntries: Int = 10,
    val comparator: Comparator<Instrument> = comparing(Instrument::date).reversed()
) : Statistic() {

    val queue: Queue<Instrument> = MinMaxPriorityQueue
        .orderedBy(comparator)
        .maximumSize(maxEntries)
        .create()

    override val metricValue get() = queue.sumByDouble(Instrument::value)

    override fun acquire(value: Instrument) {
        synchronized(queue) {
            queue.add(value)
        }
    }

    override fun toString(): String {
        return "SumOfNewest(maxEntries=$maxEntries, metricValue=${metricValue})"
    }


}