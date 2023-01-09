package com.adamblack.historyofempire.fragment

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognitionService.SupportCallback
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.adamblack.historyofempire.R
import com.adamblack.historyofempire.databinding.FragmentDetailsBinding
import com.adamblack.historyofempire.databinding.FragmentHomeBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlin.concurrent.fixedRateTimer

class DetailsFragment : Fragment() {
    private lateinit var detailsBinding: FragmentDetailsBinding

    private lateinit var navController: NavController

    private var resourceUrl:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)


    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=view.let { FragmentDetailsBinding.bind(it) }
        detailsBinding=binding

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(false){
            override fun handleOnBackPressed() {
            }
        })





        val view=requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility=View.GONE
        val toolbar=requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility=View.VISIBLE
        val appbarText=requireActivity().findViewById<TextView>(R.id.appbarText)
        val appBarLayout=requireActivity().findViewById<AppBarLayout>(R.id.homeAppBar)
        appBarLayout.visibility=View.GONE

      val title=arguments?.getSerializable("title")
        val brief=arguments?.getSerializable("details")
        val imageUrl=arguments?.getSerializable("imageUrl")
        resourceUrl=arguments?.getSerializable("resourceUrl") as String

        binding.collapsingToolBar.title=title.toString()




        //binding.detailsTitle.text=title.toString()
        binding.detailsLongText.text=brief.toString()
        Picasso.get().load(imageUrl.toString()).placeholder(R.drawable.ic_launcher_foreground).into(binding.detailsImage)

        binding.detailsBtn.setOnClickListener {
            if (resourceUrl.isNotEmpty()){
                val uri= Uri.parse(resourceUrl)
                startActivity(Intent(Intent.ACTION_VIEW,uri))
            }else{
                Toast.makeText(requireContext(),"üzgünüm henüz Url adresi girilmedi",Toast.LENGTH_SHORT).show()
            }

        }
    }





}