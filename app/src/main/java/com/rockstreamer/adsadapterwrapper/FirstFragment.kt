package com.rockstreamer.adsadapterwrapper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rockstreamer.adsadapterwrapper.databinding.FragmentFirstBinding
import com.rockstreamer.adsmodule.FBNativeAdAdapter

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
        for (i in 1..30){
            list.add("Item Number is $i")
        }
        listAdapter.addAll(list)
        val fbAdapter: FBNativeAdAdapter = FBNativeAdAdapter.Builder
            .with("321357575164976_321357668498300", listAdapter)
            .build()
        binding.recycleview.adapter = fbAdapter

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}