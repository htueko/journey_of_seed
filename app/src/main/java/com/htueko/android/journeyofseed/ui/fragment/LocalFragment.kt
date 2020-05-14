package com.htueko.android.journeyofseed.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.data.adapter.PlantAdapter
import com.htueko.android.journeyofseed.ui.viewmodel.PlantViewModel
import com.htueko.android.journeyofseed.util.swipeToDelete


class LocalFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlantViewModel::class.java)
    }

    private lateinit var mAdapter: PlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize the adapter
        mAdapter = PlantAdapter(requireActivity().applicationContext)
        // to show option menu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_local, container, false)
        // observe the data
        viewModel.getAllPlants().observe(viewLifecycleOwner, Observer { plantList ->
            // update the cache copy of the plantList in the adapter.
            plantList.let { mAdapter.addPlant(it) }
        })
        val rv = view.findViewById<RecyclerView>(R.id.recycler_view_local_fragment)
        // populate the data
        rv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        // swipe to delete function
        swipeToDelete(mAdapter, viewModel, rv)



        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.local_option_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // text has changed, apply filtering here

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // perform the final search

                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {

            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

}
