package com.rockstreamer.adsadapterwrapper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rockstreamer.adsadapterwrapper.databinding.FragmentFirstBinding
import com.rockstreamer.adsadapterwrapper.databinding.FragmentSecondBinding
import com.rockstreamer.adsmodule.AdmobBannerAdAdapter
import com.rockstreamer.adsmodule.FacebookBannerAdAdapter
import com.rockstreamer.adsmodule.FacebookNativeAdAdapter
import com.rockstreamer.adsmodule.FacebookNativeBannerAdsAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */


class FirstFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)


        binding.buttonAdmobBanner.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_admobBannerIntoAdapter)
        }

        binding.admobNative.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_admobNativeIntoAdapter)
        }

        binding.facebookBanner.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_facebookBannerIntoAdapter)
        }

        binding.facebookNative.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_facebookNativeIntoAdapter)
        }

        binding.facebookNativeBanner.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_facebookNativeBanner)
        }


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}