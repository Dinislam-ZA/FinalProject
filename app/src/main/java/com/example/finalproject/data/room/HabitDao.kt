package com.example.finalproject.data.room

import androidx.room.*
import com.example.finalproject.data.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits_table")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits_table WHERE id = :habitId")
    suspend fun getHabit(habitId: Long): Habit

    @Query("SELECT * FROM habits_table WHERE category_id = :categoryId")
    fun getHabitsForCategory(categoryId: Long): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: Habit): Long

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)
}