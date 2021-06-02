package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.utils.showToast

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var mAdapter: AsteroidListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        setUpRecyclerView()

        setUpObservers()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun setUpRecyclerView(){
        mAdapter = AsteroidListAdapter(object : AsteroidListAdapter.AsteroidClickListener {
            override fun onItemClick(asteroidData: Asteroid) {
//                showToast("CodeName : ${asteroidData.codename}")
                findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroidData))
            }
        })
        binding.asteroidRecycler.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mAdapter
        }
    }

    private fun setUpObservers(){
        viewModel.dummyAsteroids.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
        }
    }
}
