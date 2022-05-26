package com.example.data

import androidx.room.*
import com.example.domain.entities.HabitData
import com.example.domain.entities.HabitPeriodicity
import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity
@TypeConverters(
    HabitDataDto.HabitTypeConverter::class,
    HabitDataDto.HabitPriorityConverter::class,
    HabitDataDto.HabitPeriodicityConverter::class,
    HabitDataDto.HabitDatesConverter::class
)
data class HabitDataDto(
    @PrimaryKey
    @SerializedName("title")
    val name: String,
    val description: String,
    val priority: HabitPriority,
    var type: HabitType,
    val periodicity: HabitPeriodicity,
    @SerializedName("color")
    var borderColor: Int
) : Serializable {
    @SerializedName("done_dates")
    var doneDates = mutableListOf<Int>()

    var uid: String? = null
    var date: Int = 0

    companion object {
        fun fromDto(dto: HabitDataDto): HabitData {
            val habit = HabitData(
                dto.name,
                dto.description,
                dto.priority,
                dto.type,
                dto.periodicity,
                dto.borderColor
            )
            habit.date = dto.date
            habit.id = dto.uid ?: ""
            habit.doneDates = dto.doneDates
            return habit
        }

        fun toDto(habit: HabitData): HabitDataDto {
            val dto = HabitDataDto(
                habit.name,
                habit.description,
                habit.priority,
                habit.type,
                habit.periodicity,
                habit.borderColor
            )
            if (habit.id != null)
                dto.uid = habit.id
            dto.date = habit.date
            dto.doneDates = habit.doneDates
            return dto;
        }
    }

    class HabitPeriodicityConverter {
        @androidx.room.TypeConverter
        fun fromPeriodicity(periodicity: HabitPeriodicity): String {
            return "${periodicity.repeatCount},${periodicity.frequency}"
        }

        @androidx.room.TypeConverter
        fun toPeriodicity(data: String): HabitPeriodicity {
            val items = data.split(",")
            return HabitPeriodicity(items[0].toInt(), items[1].toInt())
        }
    }

    class HabitPriorityConverter {
        @androidx.room.TypeConverter
        fun fromPriority(priority: HabitPriority): String {
            return priority.toString()
        }

        @androidx.room.TypeConverter
        fun toPriority(data: String): HabitPriority {
            return when (data) {
                "High" -> HabitPriority.High
                "Medium" -> HabitPriority.Medium
                else -> HabitPriority.Low
            }
        }
    }

    class HabitTypeConverter {
        @androidx.room.TypeConverter
        fun fromType(type: HabitType): String {
            return type.toString()
        }

        @androidx.room.TypeConverter
        fun toType(data: String): HabitType {
            return when (data) {
                "Good" -> HabitType.Good
                else -> HabitType.Bad
            }
        }
    }

    class HabitDatesConverter {
        @androidx.room.TypeConverter
        fun fromDates(doneDates: MutableList<Int>): String {
            return Gson().toJson(doneDates)
        }

        @androidx.room.TypeConverter
        fun toDates(datesString: String): MutableList<Int> {
            val listType = object : TypeToken<MutableList<Int>>() {}.type
            return Gson().fromJson(datesString, listType)
        }
    }
}
