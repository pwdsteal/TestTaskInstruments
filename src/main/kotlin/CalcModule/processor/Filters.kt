package CalcModule.processor

import CalcModule.model.Instrument
import java.time.DayOfWeek

val workingDays: (Instrument) -> Boolean = {
    val dayOfWeek = it.date.dayOfWeek
    dayOfWeek !== DayOfWeek.SATURDAY && dayOfWeek !== DayOfWeek.SUNDAY
}