package com.rockstreamer.adsadapterwrapper.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rockstreamer.adsadapterwrapper.ListAdapter
import com.rockstreamer.adsadapterwrapper.databinding.FragmentFirstBinding
import com.rockstreamer.adsmodule.AdmobBannerAdAdapter
import com.rockstreamer.adsmodule.FacebookBannerAdAdapter
import com.rockstreamer.adsmodule.FacebookNativeBannerAdsAdapter

class AdmobBannerIntoAdapter : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter : ListAdapter
    private var list: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.recycleview.layoutManager = LinearLayoutManager(requireActivity())


        listAdapter = ListAdapter()
        for (i in 1..50){
            list.add("Item Number is $i")
        }
        listAdapter.addAll(list)


        val admobBanner = AdmobBannerAdAdapter.Builder.with(requireActivity() , "ca-app-pub-3940256099942544/6300978111", listAdapter)
            .adItemInterval(5).build()
        binding.recycleview.adapter = admobBanner

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}