package CalcModule.processor

import CalcModule.model.Instrument
import CalcModule.processor.statistic.*
import org.junit.Test
import java.time.LocalDate
import java.time.Month
import java.util.function.Supplier
import kotlin.random.Random
import kotlin.streams.asStream
import kotlin.test.assertEquals
import kotlin.sequences.buildSequence as buildSequence1

class ProcessorTest {

    @Test
    fun `when given N instruments with same value its mean is equal to value`() {
        val name = "INSTRUMENT1"
        val value = 1.0001
        val list = (1..10).map { Instrument(name, LocalDate.now(), value) }

        val processor = Processor(mapOf(name to SimpleStatistic(Metric.MEAN)))
        processor.process(list.stream())

        assertEquals(processor.data[name]!!.metricValue, value)
    }

    @Test
    fun `test mean statistics on closed Range`() {
        val start = LocalDate.of(2014, Month.NOVEMBER, 1)
        val end = start.plusMonths(1).minusDays(1)

        val name = "INSTRUMENT1"
        val value = 1.0001
        val list = (1..10).map { Instrument(name, start, value) }.toMutableList()

        // this should be ignored
        list.add(Instrument(name, start.minusDays(1), 0.0))
        list.add(Instrument(name, end.plusDays(1), 0.0))

        val processor = Processor(mapOf(name to StatisticOnDate(Metric.MEAN, start..end)))
        processor.process(list.stream())

        assertEquals(processor.data[name]!!.metricValue, value)
    }

    @Test
    fun `test 10 Newest sum`() {
        val name = "INSTRUMENT1"
        val value = 1.0001
        val list = (1..15).map { Instrument(name, LocalDate.now(), value) }

        val maxEntries = 10
        val processor = Processor { SumOfNewest(maxEntries) }
        processor.process(list.stream())

        assertEquals(processor.data[name]!!.metricValue, value * maxEntries)
    }


    @Test
    fun `test Max Statistic`() {
        val name = "INSTRUMENT1"
        val maxValue = 1.000000001
        val list = (1..10).map { Instrument(name, LocalDate.now(), 1.0) }.toMutableList()
        list.add(Instrument(name, LocalDate.now(), maxValue))

        val processor = Processor(mapOf(name to MaxValueStatistics()))
        processor.process(list.stream())

        assertEquals(processor.data[name]!!.metricValue, maxValue)
    }

    @Test
    fun `Test on 1 million records`() {
        val items = 1_000_000L
        val instrument = { Instrument("INSTRUMENT" + Random.nextLong(items / 10L), LocalDate.now(), 1.01) }
        val sequence = generateSequence { instrument.invoke() }


        val processor = Processor(
            mapOf(
                "INSTRUMENT1" to SimpleStatistic(Metric.MEAN),
                "INSTRUMENT2" to StatisticOnDate(Metric.MEAN, LocalDate.now()..LocalDate.now()),
                "INSTRUMENT3" to MaxValueStatistics()
            )
        ) { SumOfNewest() }
        processor.process(sequence.asStream().limit(items))

        assertEquals(processor.data["INSTRUMENT1"]!!.metricValue, 1.01)
        assertEquals(processor.data["INSTRUMENT2"]!!.metricValue, 1.01)
        assertEquals(processor.data["INSTRUMENT3"]!!.metricValue, 1.01)
    }


}