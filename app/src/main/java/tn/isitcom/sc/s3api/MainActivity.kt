package tn.isitcom.sc.s3api

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import tn.isitcom.sc.s3api.data.api.RetrofitClient
import tn.isitcom.sc.s3api.data.repository.UserRepository
import tn.isitcom.sc.s3api.databinding.ActivityMainBinding
import tn.isitcom.sc.s3api.ui.adapter.UserAdapter
import tn.isitcom.sc.s3api.ui.viewmodel.UserViewModel
import tn.isitcom.sc.s3api.ui.viewmodel.UserViewModelFactory
import tn.isitcom.sc.s3api.util.UiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    
    private val viewModel: UserViewModel by viewModels {
        val repository = UserRepository(RetrofitClient.apiService)
        UserViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        setupRefreshButton()
        observeUiState()
    }
    
    private fun setupRecyclerView() {
        adapter = UserAdapter { user ->
            Snackbar.make(
                binding.root,
                "Clic sur: ${user.name}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
    
    private fun setupRefreshButton() {
        binding.fabRefresh.setOnClickListener {
            viewModel.refreshUsers()
        }
        
        binding.retryButton.setOnClickListener {
            viewModel.refreshUsers()
        }
    }
    
    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersState.collect { state ->
                    handleUiState(state)
                }
            }
        }
    }
    
    private fun handleUiState(state: UiState<List<tn.isitcom.sc.s3api.data.model.User>>) {
        when (state) {
            is UiState.Loading -> {
                showLoading()
            }
            is UiState.Success -> {
                showSuccess(state.data)
            }
            is UiState.Error -> {
                showError(state.message)
            }
        }
    }
    
    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            usersRecyclerView.visibility = View.GONE
            errorLayout.visibility = View.GONE
        }
    }
    
    private fun showSuccess(users: List<tn.isitcom.sc.s3api.data.model.User>) {
        binding.apply {
            progressBar.visibility = View.GONE
            usersRecyclerView.visibility = View.VISIBLE
            errorLayout.visibility = View.GONE
        }
        
        adapter.submitList(users)
    }
    
    private fun showError(message: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            usersRecyclerView.visibility = View.GONE
            errorLayout.visibility = View.VISIBLE
            errorTextView.text = message
        }
    }
}
