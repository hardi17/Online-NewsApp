package me.hardi.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hardi.newsapp.data.roomdatabase.dao.ArticleDao
import me.hardi.newsapp.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppRoomDataBase  : RoomDatabase(){
    abstract fun articleDao(): ArticleDao
}