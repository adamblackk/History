package com.adamblack.historyofempire.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamblack.historyofempire.R
import com.adamblack.historyofempire.adapter.FeedAdapter
import com.adamblack.historyofempire.databinding.FragmentKindsDetailBinding
import com.adamblack.historyofempire.model.RealmModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import io.realm.Realm


class KindsDetailFragment : Fragment(),FeedAdapter.Listener {
    private lateinit var detailBinding: FragmentKindsDetailBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var kindDetailArrayList: ArrayList<RealmModel>


    private lateinit var feedAdapter: FeedAdapter
    private lateinit var anaSınıf:String
    private var realm:Realm= Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kinds_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentKindsDetailBinding.bind(view)
        detailBinding=binding

        val view=requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val toolbar=requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility=View.VISIBLE
        view.visibility=View.GONE
        val homeAppBar=requireActivity().findViewById<AppBarLayout>(R.id.homeAppBar)
        homeAppBar.visibility=View.VISIBLE

        anaSınıf= arguments?.getString("anaSınıf",null).toString()

        auth= Firebase.auth
        db= Firebase.firestore
        storage= Firebase.storage

        kindDetailArrayList=ArrayList<RealmModel>()



        binding.recyclerViewKindsDetail.layoutManager=LinearLayoutManager(requireContext())
        feedAdapter=FeedAdapter(kindDetailArrayList,this)
        binding.recyclerViewKindsDetail.adapter=feedAdapter

      //  getDataFromFireBase()
        getDataComingFromKinds()
    }
    fun getDataComingFromKinds(){
        val realmM=realm.where(RealmModel::class.java).equalTo("anaSınıf",anaSınıf).findAll()
        feedAdapter.upDateCountryList(realmM)

    }

    /*fun getDataFromFireBase(){

        db.collection(sınıf).addSnapshotListener { value, error ->
            if (error!=null){
                error.localizedMessage?.let {
                    Toast.makeText(requireContext(),"Error!:${it}", Toast.LENGTH_SHORT).show()
                }
            }
            if (value!=null){
                if (!value.isEmpty){
                    val documentss=value.documents
                    kindDetailArrayList.clear()
                    for (document in documentss){
                        val title=document.get("title") as String
                        val brief=document.get("brief") as String
                        val feedImageUrl=document.get("imageUrl") as String
                        val sınıf=document.get("sınıf") as String
                        val text=document.get("text") as String
                        val resourceUrl=document.get("resourceUrl") as String
                        val id=document.get("id")



                        val feed=FeedModel(title,brief,feedImageUrl,sınıf,text,resourceUrl,id.toString())
                        //kindDetailArrayList.add(feed)

                    }
                    feedAdapter.notifyDataSetChanged()
                }
            }else{
                Toast.makeText(requireContext(),"null", Toast.LENGTH_SHORT).show()

            }
        }



    }

     */

    override fun onItemClicked(feedModel: RealmModel) {
        val action=KindsDetailFragmentDirections.actionKindsDetailFragmentToDetailsFragment()
        action.arguments.putSerializable("title",feedModel.title)
        action.arguments.putSerializable("details",feedModel.text)
        action.arguments.putSerializable("imageUrl",feedModel.imageUrl)
        action.arguments.putSerializable("resourceUrl",feedModel.resourceUrl)
        Navigation.findNavController(requireView()).navigate(action)


    }

    override fun likeClicked(feedModel: RealmModel, position: Int) {
        TODO("Not yet implemented")
    }

    override fun bookMarkClicked(realmModel: RealmModel, position: Int) {
        TODO("Not yet implemented")
    }


}