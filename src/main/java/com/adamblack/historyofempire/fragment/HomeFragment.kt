package com.adamblack.historyofempire.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamblack.historyofempire.R
import com.adamblack.historyofempire.adapter.FeedAdapter
import com.adamblack.historyofempire.databinding.FragmentHomeBinding
import com.adamblack.historyofempire.model.RealmModel
import com.adamblack.historyofempire.viewmodel.FeedViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.feed_row.*


class HomeFragment : Fragment(),FeedAdapter.Listener {
    private lateinit var viewModel: FeedViewModel
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
   private lateinit var feedArrayList: ArrayList<RealmModel>

    private var feedAdapter=FeedAdapter(arrayListOf(),this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=view.let { FragmentHomeBinding.bind(it) }
        homeBinding=binding
        viewModel=ViewModelProvider(requireActivity()).get(FeedViewModel::class.java)
        viewModel.getDataFromRealm()

        val homeAppBar=requireActivity().findViewById<AppBarLayout>(R.id.homeAppBar)
        homeAppBar.visibility=View.VISIBLE
        val view=requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility=View.VISIBLE
        val appbarText=requireActivity().findViewById<TextView>(R.id.appbarText)
        appbarText.text="Home Page"


        auth= Firebase.auth
        db= Firebase.firestore
        storage= Firebase.storage
        feedArrayList=ArrayList<RealmModel>()

        binding.recyclerViewHome.layoutManager=LinearLayoutManager(requireContext())
        feedAdapter=FeedAdapter(feedArrayList,this) //initiation in yazdım denemee makstlı
        binding.recyclerViewHome.adapter=feedAdapter

        val decorator = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.recyclerViewHome.addItemDecoration(decorator)
        binding.recyclerViewHome.setHasFixedSize(true)  //ne işe yradığını bilmiyorum
        binding.recyclerViewHome.adapter?.notifyDataSetChanged()


       // getDataFromFireBase()

        homeBinding.swiperefreshLayout.setOnRefreshListener {
            feedArrayList.clear()
            homeBinding.recyclerViewHome.visibility=View.GONE
            //getDataFromFireBase()
          // viewModel.readFromCsv(requireContext())
                viewModel.addRealm(requireContext())
            //burada delay vermek gerekebilir yada baska bişey düşün
            viewModel.getDataFromRealm()
            //viewModel.refreshFromApi()
            homeBinding.swiperefreshLayout.isRefreshing=false
            homeBinding.recyclerViewHome.visibility=View.VISIBLE



        }
        observeLiveData()

    }
   /* fun readFromCsv(context: Context){
        var adm: Reader? = null
        try {
            adm = InputStreamReader(context.assets.open("Realm.csv"))
            val records: Iterable<CSVRecord> = CSVFormat.EXCEL.withDelimiter(";"[0])
                .withHeader("Id", "brief", "imageUrl", "resourceUrl","sınıf","text","title")
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


                    val realmModel=RealmModel()
                    realmModel.id=id
                    realmModel.brief=brief
                    realmModel.text=text
                    realmModel.imageUrl=imageUrl
                    realmModel.resourceUrl=resourceUrl
                    realmModel.sınıf=sınıf
                    realmModel.title=title

                    feedArrayList.add(realmModel)
                    feedAdapter.upDateCountryList(feedArrayList)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    */
    fun observeLiveData(){
        viewModel.histories.observe(viewLifecycleOwner, Observer {
            it?.let {
                homeBinding.recyclerViewHome.visibility=View.VISIBLE
                feedAdapter.upDateCountryList(it)


            }
        })

    }



    override fun onResume() {
        super.onResume()
        homeBinding.swiperefreshLayout.isRefreshing=false
    }



   /* private  fun getDataFromFireBase(){
        homeBinding.recyclerViewHome.visibility=View.VISIBLE
            val sınıf="random"
            feedArrayList.clear()
            db.collection(sınıf).addSnapshotListener { value, error ->
                if (error!=null){
                    error.localizedMessage?.let {
                        Toast.makeText(requireContext(),"Error!:${it}",Toast.LENGTH_SHORT).show()
                    }
                }
                if (value!=null){
                    if (!value.isEmpty){
                        val documentss=value.documents
                        feedArrayList.clear()
                        for (document in documentss){
                            val title=document.get("title") as String
                            val brief=document.get("brief") as String
                            val feedImageUrl=document.get("imageUrl") as String
                            val sınıf=document.get("sınıf") as String
                            val text=document.get("text") as String
                            val resourceUrl=document.get("resourceUrl") as String
                            val id=document.get("id")



                            val feed=FeedModel(title,brief,feedImageUrl,sınıf,text,resourceUrl,id.toString())
                           feedArrayList.add(feed)
                            viewModel.storeInSQLite(listOf(feed))

                        }
                        feedAdapter.notifyDataSetChanged()
                        Toast.makeText(requireContext(),"data from Firebase",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(),"null",Toast.LENGTH_SHORT).show()

                }
            }

        }

    */

    override fun onItemClicked(feedModel: RealmModel) {
       val action=HomeFragmentDirections.actionHomeFragmentToDetailsFragment2()
        action.arguments.putSerializable("title",feedModel.title)
        action.arguments.putSerializable("details",feedModel.text)
        action.arguments.putSerializable("imageUrl",feedModel.imageUrl)
        action.arguments.putSerializable("resourceUrl",feedModel.resourceUrl)
        Navigation.findNavController(requireView()).navigate(action)

        Toast.makeText(requireContext(),"${feedModel.sınıf}",Toast.LENGTH_SHORT).show()
    }


    override fun likeClicked(realmModel: RealmModel, position: Int) {
        Toast.makeText(requireContext(),"Henüz değil",Toast.LENGTH_SHORT).show()
    }


    override fun bookMarkClicked(realmModel: RealmModel, position: Int) {


       }






    override fun onDestroy() {
        super.onDestroy()

    }




}