package com.example.kg_books

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.kg_books.data.Const
import com.example.kg_books.data.Data
import com.example.kg_books.models.BookLocal
import java.io.File


class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        val cache = Data(this)
        val files =  cache.getDownloads()

        files.forEach {d->
            val book = BookLocal(d.book.id, d.book.name, d.book.size,
                    getExternalFilesDir(Const.booksFolder).toString()+"/"+d.book.id+"."+d.book.extension, d.book.extension)
            val file = File(book.location)
            if(file.exists()){
                cache.addBooks(book)
            }
            cache.removeDownloads(d.id)
        }

        val books = cache.getBooks()
        val new = arrayListOf<BookLocal>()

        books.forEach {
            val file = File(it.location)
            if(file.exists()) {
                new.add(it)
            }
        }
        cache.saveBooks(new)
    }

    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}