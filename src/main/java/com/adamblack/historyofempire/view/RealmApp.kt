package com.adamblack.historyofempire.view

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
            .name("Random.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)
    }
}
