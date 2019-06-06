package CalcModule

import CalcModule.model.toInstrument
import CalcModule.processor.Processor
import CalcModule.processor.statistic.*
import CalcModule.processor.workingDays
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.Month

/**
 * Запуск процессинга из файла согласно описанным условиям
 */
fun main(args: Array<String>) {
    val start = LocalDate.of(2014, Month.NOVEMBER, 1)
    val end = start.plusMonths(1).minusDays(1)

    val processor = Processor(
        mapOf(
            "INSTRUMENT1" to SimpleStatistic(Metric.MEAN),
            "INSTRUMENT2" to StatisticOnDate(Metric.MEAN,start..end),
            "INSTRUMENT3" to MaxValueStatistics()
        )
    ) { SumOfNewest() }

    val path = Paths.get("src/main/resource/123.txt")

    val stream = Files.lines(path)
        .parallel()
        .map { it.toInstrument() }
        .filter(workingDays)

    processor.process(stream)
    println(processor.data)
}

