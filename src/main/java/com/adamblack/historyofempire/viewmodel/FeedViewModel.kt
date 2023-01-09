package com.adamblack.historyofempire.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast

import androidx.lifecycle.MutableLiveData
import com.adamblack.historyofempire.adapter.FeedAdapter
import com.adamblack.historyofempire.model.FeedModel
import com.adamblack.historyofempire.model.RealmModel
import com.adamblack.historyofempire.service.HistoryDataBase
import com.adamblack.historyofempire.util.CostomSharedPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.realm.Realm
import io.realm.RealmList
import io.realm.internal.IOException
import kotlinx.coroutines.*
import org.apache.commons.csv.CSVFormat
import java.io.InputStreamReader

import java.io.Reader
import org.apache.commons.csv.CSVRecord
import java.io.BufferedReader
import java.io.InputStream


class FeedViewModel(application: Application):BaseViewModel(application){
    private lateinit var db: FirebaseFirestore
    private lateinit var feedArrayList: ArrayList<RealmModel>
    private val costomPreferences= CostomSharedPreferences(getApplication())
    private var refreshTime=10*60*1000*1000*1000L
    private var realm: Realm = Realm.getDefaultInstance()






    val histories= MutableLiveData<List<RealmModel>>()





    fun refreshData(){
        val updataTime=costomPreferences.getTime()
        if (updataTime!=null &&updataTime!=0L && System.nanoTime()-updataTime <refreshTime){
            getDataFromSQLite()
        }else{
           // getDataFromFirebase()

        }
    }

     fun getDataFromSQLite(){
    launch {
        val history=HistoryDataBase(getApplication()).hidtoryDao().getAllList()
        showHistories(history)
        Toast.makeText(getApplication(),"Histories from SQLite", Toast.LENGTH_SHORT).show()

    }
    }
     fun showHistories(historyList:List<FeedModel>){
       // histories.value=historyList

    }
    @SuppressLint("SuspiciousIndentation")
     fun storeInSQLite(list: List<FeedModel>){
        launch {
        val dao=HistoryDataBase(getApplication()).hidtoryDao()
            dao.deleteAllHistories()
            val listLong=dao.insertAll(*list.toTypedArray())
            var i=0
            while (i < list.size){
                list[i].roomId=listLong[i].toInt()
                i += 1
            }
            showHistories(list)
        }

        costomPreferences.saveTime(System.nanoTime())
    }

    fun readFromCsv(context: Context) {


        var adm: Reader? = null
        try {
            adm = InputStreamReader(context.assets.open("Realm.csv"))
            val records: Iterable<CSVRecord> = CSVFormat.EXCEL.withDelimiter(";"[0])
                .withHeader("Id", "brief", "imageUrl", "resourceUrl","sınıf","text","title","anaSınıf")
                .parse(adm)

            for (record in records) {
                val id = record["Id"]

                if (id != null && id != "Id" && id != "\uFEFFId") {
                    val id = id.toInt()
                    val brief = record["brief"]
                    val imageUrl = record["imageUrl"]
                    val resourceUrl = record["resourceUrl"]
                    val sınıf=record["sınıf"]
                    val text=record["text"]
                   val title=record["title"]
                    val anaSınıf=record["anaSınıf"]

                    realm.beginTransaction()
                      val realmModel=  realm.createObject(RealmModel::class.java)
                        realmModel.Id=id
                        realmModel.brief=brief
                        realmModel.text=text
                        realmModel.imageUrl=imageUrl
                        realmModel.resourceUrl=resourceUrl
                        realmModel.sınıf=sınıf
                        realmModel.title=title
                        realmModel.anaSınıf=anaSınıf
                      //  val list=RealmList<RealmModel>()
                        //realm.deleteAll()

                    //list.add(realmModel)
                    realm.commitTransaction()
                //problemsiz csv yi realm e kaydettim ....
                    //burada tek eksik ingilizce karakterler dısında okuduğunda ?? harfler böyle geliyor
                    //bunun onune gecmek için json formatında verileri kaydettim değişen bişey olamdı işimi gördü

                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }


        }


    fun addRealm(context: Context){
        val json1=context.assets.open("csv.json")  //bu sekilde json dosyasını kaydedebiliyorum
       /* val json="[{\"Id\":1,\"brief\":\"Bebek, bir insanın en küçük hali olan doğum anından itibaren yürüme dönemine kadar olan zaman diliminde aldığı isimdir\",\"imageUrl\":\"https://firebasestorage.googleapis.com/v0/b/historyofempire-3c65e.appspot.com/o/bebek%2Fbebek.jpg?alt=media&token=dd7cf300-32a2-4eb7-9f2a-f9b10557efe9\",\"resourceUrl\":\"https://tr.wikipedia.org/wiki/Bebek\",\"sınıf\":\"bebek\",\"text\":\"Yeni doğmuş olan bir bebek her açıdan anne ve babasına muhtaçtır. Sağlıklı bir bebeğin ağırlığı ortalama 3.2 kg boyu ise 35–50 cm arası olabilir. Erkek bebeklerin ağırlığı kız bebeklere oranla biraz daha fazla olabilir.  İlk aylarda bebeğin gelişimini yakından takip etmek ve bu süreç içerisinde bebekle yakından ilgilenmek çok önemlidir. İlk aylarda bebek, el ve ayaklarını refleks olarak oynatabilir. Yüzüstü yatarken başını çok kısa bir süre için bile olsa yukarı kaldırabilir. Havada ayaküstü tutulunca yürüme hareketleri yapmaya çalışır. Parlak ve hareket eden nesneleri özellikle de etrafında hareket eden insanları izler. Kendisiyle konuşulmasından çok hoşlanır. İşitme duyusu zamanla daha da güçlenir ve seslere karşı tepki vermeye başlar. Yakınlarının özellikle annesinin göz ve ten temasından hoşlanır.\",\"title\":\"BEBEK\"}" +
                ",{\"Id\":2,\"brief\":\"Atlar cok duygusal hayvanlardır özellikle onlarla duygusal bağınızı biraz ols..\",\"imageUrl\":\"https://firebasestorage.googleapis.com/v0/b/historyofempire-3c65e.appspot.com/o/random%2Fhorse.jpg?alt=media&token=21740ddb-eb9f-44ff-8515-92441de1e95c\",\"resourceUrl\":\"https://tr.wikipedia.org/wiki/At\",\"sınıf\":\"horse\",\"text\":\"İnsanlara hizmet eden hayvanların en kabiliyetlilerindendir. İnsanların, harp meydanlarında, izinsiz gösteri kontrolünde, yük taşımada, yarış, cirit, çit atlama ve av sporlarında yardımcısıdır. Silah gürültüsüne ve bando sesine rahatlıkla alışır. Atlar aynı zamanda dizlerini kilitleyebilir.  At, cesur ve atılgan olduğu gibi sahibine son derece itaatkârdır. Sahibi dilerse dolu dizgin, dörtnala koşar, isterse aheste yürür, isterse durur. Her durumda sahibini memnun etmeye dikkat eder. Yorgunluğa bakmaksızın kendini çatlatmak pahasına da olsa olanca gayret ve kuvvetini itaat uğruna sarf eder.  Atlar, bacak kemiklerinin kilitlenme özelliği sayesinde ayakta uyurlar ve kendilerini güvende hissederlerse yatarak da uyuyabilirler. Bu şekilde, ayakta uyurken yırtıcı hayvanlara karşı tetikte olurlar. Yatarak uyumak atlar için daha sağlıklıdır. Bir at yatarak uyuduğunda sürüdeki diğer atlardan biri yanında ayakta durur veya derin olmayan biçimde ayakta uyur. Tamamen yalnız olan bir at içgüdülerinin tehlike uyarısı nedeniyle hiç derin uyuyamaz ve bu nedenle uyku kalitesi düşer\",\"title\":\"horse\"}" +
                ",{\"Id\":3,\"brief\":\"kanuni sulatn dayanmıs viyana viyana kapısına ..süleyman\",\"imageUrl\":\"https://firebasestorage.googleapis.com/v0/b/historyofempire-3c65e.appspot.com/o/random%2Fkanuni.jpg?alt=media&token=29f9edf3-973f-4f13-90c5-709d44445127\",\"resourceUrl\":\"https://tr.wikipedia.org/wiki/I._S%C3%BCleyman\",\"sınıf\":\"Osmanlı\",\"text\":\"6 Kasım 1494 tarihinde, Trabzon'da doğdu.[8] Babası, Süleyman doğduğu zaman Trabzon valisi olan ve 1512 yılında padişah olarak tahta çıkan I. Selim, annesi ise Ayşe Hafsa Valide sultandı.[9] Çocukluk yıllarını, süt kardeşi Yahya Efendi ile birlikte Trabzon'da geçirdi.[10] 7 yaşındayken; bilim, tarih, edebiyat, din ve askeriye alanlarında eğitim almak için İstanbul'a, Topkapı Sarayı'ndaki Enderûn'a gönderildi.  1508 yılında Şarkî Karahisar Sancak Beyi olarak atandı ancak babası Selim'in kardeşi, Amasya Sancak Beyi Ahmed'in itirazı sonrasında Bolu'ya atandı.[10][11] Ahmed'in buna da itiraz etmesi sebebiyle atandığı Kefe sancağına 1509 Temmuz'unda çıktı.[11][12] Babası I. Selim'in 1512'de tahta çıkmasından sonra İstanbul ve Edirne'de oturdu.[12] 1513 yılında Saruhan sancak beyliğine atandı.[13] Burada, sonraları başdanışmanlarından biri olacak olan Pargalı İbrahim ile yakın bir arkadaşlık kurdu.[12][14] Yaklaşık yedi yıllık Saruhan sancak beyliğinin ardından, 1520 yılının 21 Eylül'ü 22 Eylül'e bağlayan gecesi babası I. Selim'in ölümü üzerine İstanbul'a hareket etti ve tahtta hak iddia edecek başka biri olmadığından herhangi bir mücadele vermeden 30 Eylül 1520 tarihinde onuncu Osmanlı padişahı olarak tahta çıktı.[12][15] Tahta geçişinden birkaç hafta sonra Venedik Elçisi Bartolomeo Contarini, Süleyman'ı \\\"Yirmi altı yaşında, uzun fakat sırım gibi ve kibar görünüşlü. Boynu biraz fazla uzun, yüzü zayıf, burnu kartal gagası gibi kıvrık. Gölge gibi bıyığı ve küçük bir sakalı var. Cildi biraz soluk olsa da yüzü oldukça hoş. Derisi solgunluğa meyilli. Akıllı bir hükümdar olduğu söyleniyor ve herkes onun saltanatının hayırlı olacağını umuyor.\\\" şeklinde tanımlamıştır.[16]\",\"title\":\"Tarih\"}]"

        */

        try {
            realm.executeTransactionAsync {
               // it.createAllFromJson(RealmModel::class.java,json1)
           // it.deleteAll()
                //burada json verilerini problemsiz kaydediyorum
            }

        }catch (e:IOException){
            throw RuntimeException(e)
        }

    }
    fun updataRealm(){

    }

     fun  getDataFromRealm() {

        val realmM=realm.where(RealmModel::class.java).findAll()

         println("--->:${realm}")
         realmM.let {
             histories.value=realmM.subList(0,realmM.size)
             println("okkk:${realmM.subList(0,realmM.size)}")

         }

    }
    fun refreshFromApi(){
       // getDataFromFirebase()
    }
   /* fun getDataFromFirebase()  {

                db = Firebase.firestore
                db.collection("random").addSnapshotListener { value, error ->
                    if (error != null) {
                        error.localizedMessage?.let {
                            Toast.makeText(getApplication(), "Error!:${it}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    if (value != null) {
                        if (!value.isEmpty) {
                            val documentss = value.documents
                            for (document in documentss) {
                                val title = document.get("title") as String
                                val brief = document.get("brief") as String
                                val feedImageUrl = document.get("imageUrl") as String
                                val sınıf = document.get("sınıf") as String
                                val text = document.get("text") as String
                                val resourceUrl = document.get("resourceUrl") as String
                                val id = document.get("id")

                                        println("Reading the file of users")

                                val feed=FeedModel(title,brief,feedImageUrl,sınıf,text,resourceUrl,id.toString())

                                storeInSQLite(listOf(feed))


                                //not firebaseden liste halinde cekemedim yarın bak ....
                                Toast.makeText(getApplication(),"data from Firebase",Toast.LENGTH_SHORT).show()



                            }

                        }
                    }

                }





        }

    */

        }






