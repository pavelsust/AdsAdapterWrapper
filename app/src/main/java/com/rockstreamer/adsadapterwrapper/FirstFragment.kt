package com.rockstreamer.adsadapterwrapper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rockstreamer.adsadapterwrapper.databinding.FragmentFirstBinding
import com.rockstreamer.adsmodule.AdmobNativeAdAdapter
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
        for (i in 1..50){
            list.add("Item Number is $i")
        }
        listAdapter.addAll(list)


        val fbAdapter: FBNativeAdAdapter = FBNativeAdAdapter.Builder
            .with("424421591789497_990179275213723", 5, listAdapter)
            .build()


        val admobNativeAdAdapter = AdmobNativeAdAdapter.Builder.with("ca-app-pub-3940256099942544/2247696110", listAdapter,
            "medium"
        ).adItemInterval(5).build()
        binding.recycleview.adapter = admobNativeAdAdapter

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}