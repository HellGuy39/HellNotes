import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HNDateHandler private constructor(
    private val localDateTime: LocalDateTime,
) {

    fun isInPast(): Boolean {
        val currentMillis = currentMillis()
        val handledDateMillis = this.toMillis()
        return handledDateMillis < currentMillis
    }

    fun isInFeature(): Boolean {
        val currentMillis = currentMillis()
        val handledDateMillis = this.toMillis()
        return handledDateMillis > currentMillis
    }

    fun toMillis(): Long {
        return localDateTime.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun toLocalDateTime(): LocalDateTime {
        return localDateTime
    }

    fun format(pattern: String): String {
        return localDateTime.atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern(pattern))
    }

    fun formatBest(): String {
        val currentLocalDateTime = LocalDateTime.now()
        val handledLocalDateTime = this.localDateTime

        return when {
            currentLocalDateTime.year != handledLocalDateTime.year -> {
                format(DefaultPatterns.YEAR_MONTH_DAY_TIME)
            }
            currentLocalDateTime.month != handledLocalDateTime.month -> {
                format(DefaultPatterns.MONTH_DAY_TIME)
            }
            currentLocalDateTime.dayOfMonth != handledLocalDateTime.dayOfMonth -> {
                format(DefaultPatterns.MONTH_DAY_TIME)
            }
            else -> {
                format(DefaultPatterns.TIME_ONLY)
            }
        }
    }

    fun increaseDays(days: Long): HNDateHandler {
        localDateTime.plusDays(days)
        return this
    }

    fun increaseWeeks(weeks: Long): HNDateHandler {
        localDateTime.plusWeeks(weeks)
        return this
    }

    fun increaseMonths(months: Long): HNDateHandler {
        localDateTime.plusMonths(months)
        return this
    }

    object DefaultPatterns {
        const val TIME_ONLY = "HH:mm"
        const val DATE_ONLY = "MMMM dd"

        const val MONTH_DAY_TIME = "dd MMMM, HH:mm"
        const val YEAR_MONTH_DAY_TIME = "dd MMMM yyyy, HH:mm"
        const val YEAR_MONTH_DAY = "dd.MM.yyyy"
    }

    object SpecialPatterns {
        const val ISO_8061 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val NEW_FILE = "MMM_dd_yyyy_HH_mm_ss"
        const val CHANGELOG_RELEASE = "dd/MM/yyyy"
    }

    companion object {

        fun currentMillis() = System.currentTimeMillis()

        fun now(): HNDateHandler {
            return HNDateHandler(LocalDateTime.now())
        }

        fun from(localDateTime: LocalDateTime): HNDateHandler {
            return HNDateHandler(localDateTime)
        }

        fun from(millis: Long): HNDateHandler {
            val localDateTime = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            return HNDateHandler(localDateTime)
        }

        fun from(value: String?, pattern: String): HNDateHandler {
            val formatter = DateTimeFormatter.ofPattern(pattern)
                .withZone(ZoneId.systemDefault())
            val localDateTime = LocalDateTime.parse(value, formatter)
            return HNDateHandler(localDateTime)
        }
    }
}