package com.example.task2.models

enum class HabitType(val value: Int) {
    Good(0),
    Bad(1);

    companion object {
        private val VALUES = values()
        fun from(value: Int): HabitType = VALUES.first { it.value == value }
    }
}