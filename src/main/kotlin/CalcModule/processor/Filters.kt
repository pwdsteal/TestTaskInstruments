package CalcModule.processor

import CalcModule.model.Instrument
import java.time.DayOfWeek

/**
 * Фильтр по рабочим дням
 */
val workingDays: (Instrument) -> Boolean = {
    val dayOfWeek = it.date.dayOfWeek
    dayOfWeek !== DayOfWeek.SATURDAY && dayOfWeek !== DayOfWeek.SUNDAY
}