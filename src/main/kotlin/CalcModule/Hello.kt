package CalcModule

import CalcModule.model.toInstrument
import CalcModule.processor.Processor
import CalcModule.processor.statistic.MaxValueStatistics
import CalcModule.processor.statistic.SimpleStatistic
import CalcModule.processor.statistic.StatisticOnDate
import CalcModule.processor.statistic.SumOfNewest
import CalcModule.processor.workingDays
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.Month

fun main(args: Array<String>) {
    val start = LocalDate.of(2014, Month.NOVEMBER, 1)
    val end = start.plusMonths(1).minusDays(1)

    val processor = Processor(
        mapOf(
            "INSTRUMENT1" to SimpleStatistic(),
            "INSTRUMENT2" to StatisticOnDate(start..end),
            "INSTRUMENT3" to MaxValueStatistics()
        )
    ) { SumOfNewest() }

    val path = Paths.get("/Users/oleg/IdeaProjects/CaclModule/src/test/resource/123.txt")

    val stream = Files.lines(path)
        .parallel()
        .map { it.toInstrument() }
        .filter(workingDays)

    processor.process(stream)
}

