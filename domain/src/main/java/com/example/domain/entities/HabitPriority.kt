package com.example.domain.entities

enum class HabitPriority(val value: Int) {
    Low(0),
    Medium(1),
    High(2);

    companion object {
        private val VALUES = values()
        fun from(value: Int): HabitPriority = VALUES.first { it.value == value }
    }
}