package com.taichi.prompts.base

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [AvatarEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun avatarDao(): AvatarDao
}

@Entity(tableName = "avatar_table")
data class AvatarEntity(
    @PrimaryKey val id: Int = 1, // 固定 ID 为 1，保证只有一条记录
    val imagePath: String
)

@Dao
interface AvatarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAvatar(avatar: AvatarEntity)

    @Query("SELECT * FROM avatar_table WHERE id = 1")
    fun getAvatar(): AvatarEntity?
}