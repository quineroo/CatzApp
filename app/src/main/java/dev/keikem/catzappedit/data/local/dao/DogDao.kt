package dev.keikem.catzappedit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.keikem.catzappedit.data.local.entity.LocalDog

@Dao
interface DogDao {

    @Query("SELECT * from dog_table")
    fun get() : LocalDog?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(cat: LocalDog)
}