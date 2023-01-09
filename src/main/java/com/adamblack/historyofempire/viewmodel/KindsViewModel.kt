package com.adamblack.historyofempire.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.adamblack.historyofempire.model.RealmModel
import io.realm.Realm

class KindsViewModel(application: Application):BaseViewModel(application) {
    private var realm: Realm = Realm.getDefaultInstance()

    val kinds= MutableLiveData<List<RealmModel>>()



    fun getKindsFromRealm(){

           val realmM= realm.where(RealmModel::class.java).equalTo("sınıf","Random").findAll()
            kinds.value=realmM.subList(0,realmM.size)
            println("okkk:${realmM.subList(0,realmM.size)}")

    }
}