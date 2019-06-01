package CalcModule.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Instrument(
    val name: String,
    val date: LocalDate,
    val value: Double
)

val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")

fun String.toInstrument(): Instrument {
    val list = split(',')
    return Instrument(
        name = list[0],
        date = LocalDate.parse(list[1], formatter),
        value = list[2].toDouble()
    )
}