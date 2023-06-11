package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.model.Habit
import com.example.finalproject.data.room.AppDatabase
import com.example.finalproject.data.room.HabitDao
import kotlinx.coroutines.flow.Flow

class HabitRepo(val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val habitDao = db.habitsDao()

    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()


    suspend fun insert(habit: Habit): Long {
        return habitDao.insert(habit)
    }

    suspend fun update(habit: Habit) {
        habitDao.update(habit)
    }

    suspend fun delete(habit: Habit) {
        habitDao.delete(habit)
    }

    suspend fun getHabit(habitId: Long): Habit {
        return habitDao.getHabit(habitId)
    }

    fun getHabitsForCategory(categoryId: Long): Flow<List<Habit>> {
        return habitDao.getHabitsForCategory(categoryId)
    }
}

