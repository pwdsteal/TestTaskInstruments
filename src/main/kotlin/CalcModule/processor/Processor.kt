package CalcModule.processor

import CalcModule.model.Instrument
import java.util.concurrent.ConcurrentHashMap

class Processor(config: Map<String, Statistic>) {

    val data = ConcurrentHashMap(config)

    fun process(instrument: Instrument) {
        // Insert newest statistic for unknown
        data.computeIfAbsent(instrument.name) { SumOfNewest() }
        data[instrument.name]!!.acquire(instrument)
    }




    enum class Metric {
        MEAN,
        SUM
    }
}



