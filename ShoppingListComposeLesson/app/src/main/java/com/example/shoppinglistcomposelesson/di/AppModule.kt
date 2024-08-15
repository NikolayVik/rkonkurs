package com.example.shoppinglistcomposelesson.di

import android.app.Application
import androidx.room.Room
import com.example.shoppinglistcomposelesson.data.AddItemRepoImpl
import com.example.shoppinglistcomposelesson.data.AddItemRepository
import com.example.shoppinglistcomposelesson.data.MainDb
import com.example.shoppinglistcomposelesson.data.NoteRepoImpl
import com.example.shoppinglistcomposelesson.data.NoteRepository
import com.example.shoppinglistcomposelesson.data.ShoppingListRepoImpl
import com.example.shoppinglistcomposelesson.data.ShoppingListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb{
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "shop_list_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideShopRepo(db: MainDb): ShoppingListRepository{
        return ShoppingListRepoImpl(db.shoppingListDao)
    }

    @Provides
    @Singleton
    fun provideAddItemRepo(db: MainDb): AddItemRepository{
        return AddItemRepoImpl(db.addItemDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepo(db: MainDb): NoteRepository{
        return NoteRepoImpl(db.noteDao)
    }
}
