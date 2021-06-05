package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.Asteroid

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(dataSource)).get(MainViewModel::class.java)
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var mAdapter: AsteroidListAdapter
    private lateinit var dataSource: AsteroidDatabaseDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        dataSource = AsteroidDatabase.getInstance(requireContext().applicationContext).asteroidDatabaseDao

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

        return when (item.itemId) {
            R.id.show_all_menu -> { //Week Asteroids
                viewModel.getWeekAsteroidsDataFromDb()
                true
            }
            R.id.show_rent_menu -> {    //Today Asteroids
                viewModel.getTodayAsteroidsDataFromDb()
                true
            }
            R.id.show_buy_menu -> {
                true
            }
            else -> true
        }
    }

    private fun setUpRecyclerView(){
        mAdapter = AsteroidListAdapter(object : AsteroidListAdapter.AsteroidClickListener {
            override fun onItemClick(asteroidData: Asteroid) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroidData))
            }
        })

        binding.asteroidRecycler.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mAdapter
        }
    }

    private fun setUpObservers(){
        viewModel.asteroids.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
        }
    }
}