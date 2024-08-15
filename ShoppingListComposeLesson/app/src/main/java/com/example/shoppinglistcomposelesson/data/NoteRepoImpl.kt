package com.example.shoppinglistcomposelesson.data

import kotlinx.coroutines.flow.Flow

class NoteRepoImpl(
    private val dao: NoteDao
) : NoteRepository {
    override suspend fun insertItem(item: NoteItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: NoteItem) {
        dao.deleteItem(item)
    }

    override fun getAllItemsById(): Flow<List<NoteItem>> {
        return dao.getAllItemsById()
    }

    override suspend fun getNoteItemById(id: Int): NoteItem {
        return dao.getNoteItemById(id)
    }
}
