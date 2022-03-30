package com.rockstreamer.adsadapterwrapper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rockstreamer.adsadapterwrapper.databinding.FragmentFirstBinding
import com.rockstreamer.adsmodule.AdmobBannerAdAdapter
import com.rockstreamer.adsmodule.FacebookBannerAdAdapter
import com.rockstreamer.adsmodule.FacebookNativeAdAdapter
import com.rockstreamer.adsmodule.FacebookNativeBannerAdsAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */


class FirstFragment : Fragment() {

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


        val facebookAdapter: FacebookNativeBannerAdsAdapter = FacebookNativeBannerAdsAdapter.Builder
            .with("381521685984607_381553192648123", 5, listAdapter)
            .build()


        val admobNativeAdAdapter = FacebookBannerAdAdapter.Builder.with(requireActivity(),"381521685984607_1157867285016706", listAdapter,
        ).adItemInterval(3).build()

        binding.recycleview.adapter = admobNativeAdAdapter

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}