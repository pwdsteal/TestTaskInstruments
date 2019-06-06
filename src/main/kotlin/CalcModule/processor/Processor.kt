package CalcModule.processor

import CalcModule.model.Instrument
import CalcModule.processor.statistic.Statistic
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Stream
import kotlin.system.measureTimeMillis

/**
 * Обходит стрим и считает статистику
 */
class Processor(
    config: Map<String, Statistic> = mapOf(),
    val other: (() -> Statistic)? = null
) {

    val data = ConcurrentHashMap(config)

    fun process(stream: Stream<Instrument>) {
        val count = AtomicInteger()
        val millis = measureTimeMillis {
            stream
                .peek { count.incrementAndGet() }
                .parallel()
                .forEach(this::processInstrument)
        }
        println("Scanned $count in ${Duration.ofMillis(millis)}")
    }

    private fun processInstrument(instrument: Instrument) {
        // Insert newest statistic for unknown
        other?.let { data.computeIfAbsent(instrument.name) { other.invoke() } }
        data[instrument.name]!!.acquire(instrument)
    }


}



