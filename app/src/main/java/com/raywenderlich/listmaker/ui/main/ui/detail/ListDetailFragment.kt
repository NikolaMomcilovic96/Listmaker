package com.raywenderlich.listmaker.ui.detail.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.listmaker.MainActivity
import com.raywenderlich.listmaker.TaskList
import com.raywenderlich.listmaker.databinding.ListDetailFragmentBinding
import com.raywenderlich.listmaker.ui.main.MainViewModelFactory
import com.raywenderlich.listmaker.ui.main.ui.detail.ListDetailViewModel
import com.raywenderlich.listmaker.ui.main.ui.detail.ListItemsRecycleViewAdapter

class ListDetailFragment : Fragment() {
    lateinit var binding: ListDetailFragmentBinding

    companion object {
        fun newInstance() = ListDetailFragment()
    }

    private lateinit var viewModel: ListDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListDetailFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(requireActivity()))
        ).get(ListDetailViewModel::class.java)

        val list: TaskList? = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)
        if (list != null) {
            viewModel.list = list
            requireActivity().title = list.name
        }

        val recyclerAdapter = ListItemsRecycleViewAdapter(viewModel.list)
        //binding.listItemsRecyclerView.adapter = recyclerAdapter
        //binding.listItemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.onTaskAdded = {
            recyclerAdapter.notifyDataSetChanged()
        }
    }
}