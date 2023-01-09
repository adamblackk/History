package com.adamblack.historyofempire.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamblack.historyofempire.R
import com.adamblack.historyofempire.adapter.KindsAdapter
import com.adamblack.historyofempire.databinding.FragmentKindsBinding
import com.adamblack.historyofempire.model.FeedModel
import com.adamblack.historyofempire.model.RealmModel
import com.adamblack.historyofempire.viewmodel.KindsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class KindsFragment : Fragment(),KindsAdapter.Listener {
    private lateinit var kindBinding: FragmentKindsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var kindArrayList: ArrayList<RealmModel>
    private lateinit var kindAdapter:KindsAdapter
    private lateinit var viewModel: KindsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kinds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=view.let { FragmentKindsBinding.bind(it) }
        kindBinding=binding
        viewModel=ViewModelProvider(requireActivity()).get(KindsViewModel::class.java)
        viewModel.getKindsFromRealm()


        val view=requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val toolbar=requireActivity().findViewById<Toolbar>(R.id.toolbar)
        view.visibility=View.VISIBLE
        toolbar.visibility=View.GONE
        val appbarText=requireActivity().findViewById<TextView>(R.id.appbarText)
        appbarText.text="More Topic"

        auth= Firebase.auth
        db= Firebase.firestore
        storage= Firebase.storage


        kindArrayList=ArrayList<RealmModel>()

        binding.recyclerviewKind.layoutManager=GridLayoutManager(requireContext(),2)
        kindAdapter=KindsAdapter(kindArrayList,this@KindsFragment)
        binding.recyclerviewKind.adapter=kindAdapter


        val decorator = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.recyclerviewKind.addItemDecoration(decorator)
        binding.recyclerviewKind.setHasFixedSize(true)  //ne işe yradığını bilmiyorum
        binding.recyclerviewKind.adapter?.notifyDataSetChanged()

       // getDataFromFirebase()
        observeLiveData()

    }
        private fun observeLiveData(){
            viewModel.kinds.observe(viewLifecycleOwner, Observer {
                kindBinding.recyclerviewKind.visibility=View.VISIBLE
                kindArrayList.addAll(it)
            })
        }

   /* private fun getDataFromFirebase(){
        db.collection("random").addSnapshotListener { value, error ->
                if (error!=null){
                    error.localizedMessage?.let {
                        Toast.makeText(requireContext(),"error:${it}",Toast.LENGTH_SHORT).show()
                    }
                }
                if (value!=null){
                    if (!value.isEmpty){
                        val documentss=value.documents
                        kindArrayList.clear()
                        for (doc in documentss){
                            val imageUrl=doc.get("imageUrl") as String
                            val title=doc.get("title") as String
                            val brief=doc.get("brief") as String
                            val sınıf=doc.get("sınıf") as String
                            val text=doc.get("text") as String
                            val resourceUrl=doc.get("resourceUrl") as String
                            val id=doc.get("id")

                           // val kind=FeedModel(title,brief,imageUrl,sınıf,text,resourceUrl,id.toString())
                            //kindArrayList.add(kind)
                        }
                        kindAdapter.notifyDataSetChanged()
                    }
                    }else{
                    Toast.makeText(requireContext(),"nulllllll",Toast.LENGTH_SHORT).show()
                }
                }
    }

    */

    override fun onItemClicked(feedModel: RealmModel) {
        val action=KindsFragmentDirections.actionKindsFragmentToKindsDetailFragment2()
        action.arguments.putString("anaSınıf",feedModel.anaSınıf)
        Navigation.findNavController(requireView()).navigate(action)

        //Toast.makeText(requireContext(),"sınıf:${feedModel.sınıf}",Toast.LENGTH_SHORT).show()
    }

}