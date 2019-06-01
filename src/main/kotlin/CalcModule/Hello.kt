package CalcModule

import CalcModule.model.toInstrument
import CalcModule.processor.Processor
import CalcModule.processor.statistic.SimpleStatistic
import CalcModule.processor.statistic.StatisticOnDate
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.Month
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val path = Paths.get("/Users/oleg/IdeaProjects/CaclModule/src/test/resource/123.txt")
    val stream = Files.lines(path)

    val start = LocalDate.of(2014, Month.NOVEMBER, 1)
    val end = start.plusMonths(1).minusDays(1)

    val processor = Processor(
        mapOf(
            "INSTRUMENT1" to SimpleStatistic(),
            "INSTRUMENT2" to StatisticOnDate(start..end)
        )
    )

    val count = AtomicInteger()
    val millis = measureTimeMillis {
        stream
            .peek { count.incrementAndGet() }
            .parallel()
            .map { it.toInstrument() }
            .forEach(processor::process)
    }
    println("Scanned $count in $millis")
    println(processor.data)
}

