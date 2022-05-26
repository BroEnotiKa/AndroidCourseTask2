package com.example.data

import com.example.domain.entities.HabitPeriodicity
import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class HabitDataTypeAdapter : TypeAdapter<HabitDataDto>() {
    override fun write(writer: JsonWriter?, data: HabitDataDto?) {
        data ?: return
        writer?.let { w ->
            w.beginObject()
            w.name("color").value(data.borderColor)
            w.name("count").value(data.periodicity.repeatCount)
            w.name("date").value(data.date)
            w.name("description").value(data.description)
            w.name("frequency").value(data.periodicity.frequency)
            w.name("priority").value(data.priority.value)
            w.name("title").value(data.name)
            w.name("type").value(data.type.value)
            w.name("done_dates").beginArray()
            data.doneDates.forEach { w.value(it) }
            w.endArray()
            if (data.uid != null)
                w.name("uid").value(data.uid)
            w.endObject()
        }
    }

    override fun read(reader: JsonReader?): HabitDataDto {
        var habitName = ""
        var description = ""
        var priority = 0
        var type = 0
        var repeatCount = 0
        var frequency = 0
        var borderColor = 0
        var uid = ""
        var date = 0
        val doneDates = mutableListOf<Int>()

        reader?.let { r ->
            r.beginObject()
            while (r.hasNext()) {
                val key = r.nextName()
                if (r.peek() == JsonToken.NULL) {
                    r.nextNull()
                    continue
                }

                when (key) {
                    "title" -> habitName = r.nextString()
                    "description" -> description = r.nextString()
                    "priority" -> priority = r.nextInt()
                    "type" -> type = r.nextInt()
                    "count" -> repeatCount = r.nextInt()
                    "frequency" -> frequency = r.nextInt()
                    "color" -> borderColor = r.nextInt()
                    "uid" -> uid = r.nextString()
                    "date" -> date = r.nextInt()
                    "done_dates" -> {
                        r.beginArray()
                        while (r.peek() != JsonToken.END_ARRAY)
                            doneDates.add(r.nextInt())
                        r.endArray()
                    }
                }
            }
            r.endObject()
        }

        val habit = HabitDataDto(
            habitName,
            description,
            HabitPriority.from(priority),
            HabitType.from(type),
            HabitPeriodicity(repeatCount, frequency),
            borderColor
        )
        habit.uid = uid
        habit.date = date
        habit.doneDates = doneDates

        return habit
    }
}