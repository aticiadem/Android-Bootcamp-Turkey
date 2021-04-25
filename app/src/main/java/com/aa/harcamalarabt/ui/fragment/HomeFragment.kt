package com.aa.harcamalarabt.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.adapter.ExpenseRecyclerAdapter
import com.aa.harcamalarabt.databinding.FragmentHomeBinding
import com.aa.harcamalarabt.model.CurrencyModel
import com.aa.harcamalarabt.model.ExpenseModel
import com.aa.harcamalarabt.service.CurrencyAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ExpenseRecyclerAdapter
    private val service = CurrencyAPIService()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var currencyDataList: List<CurrencyModel>
    private lateinit var expenseList: ArrayList<ExpenseModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseList = ArrayList()
        currencyDataList = ArrayList()
        getData()

        val layoutManager = LinearLayoutManager(requireActivity())

        adapter = ExpenseRecyclerAdapter(expenseList)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        val sharedPreferences = requireActivity().getSharedPreferences("Name", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name","İsim Giriniz")

        when(sharedPreferences.getInt("gender",3)){
            1 -> binding.textViewUserName.text = "$name Bey"
            2 -> binding.textViewUserName.text = "$name Hanım"
            3 -> binding.textViewUserName.text = name
        }

        binding.textViewUserName.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToChangeNameF()
            Navigation.findNavController(it).navigate(action)
        }

        binding.fab.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddExpenseF()
            Navigation.findNavController(it).navigate(action)
        }

        binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))

        binding.buttonDolar.setOnClickListener {
            changeColor(it as Button)
            getData()
        }

        binding.buttonSterlin.setOnClickListener {
            changeColor(it as Button)
            getData()
        }

        binding.buttonEuro.setOnClickListener {
            changeColor(it as Button)
            getData()
        }

        binding.buttonTl.setOnClickListener {
            changeColor(it as Button)
            getData()
        }

    }

    private fun changeColor(button: Button){
        binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        button.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
    }

    private fun getData(){
        compositeDisposable.add(service.loadData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<CurrencyModel>(){
                override fun onSuccess(t: CurrencyModel) {
                    currencyDataList = listOf(t)
                    println("Başardık Abi")
                    for(i in currencyDataList){
                        println(i.rates)
                    }
                }
                override fun onError(e: Throwable) {
                    println("Error")
                }
            }))
    }

    private fun onClickFab(view: View){
        val action = HomeFragmentDirections.actionHomeFragmentToAddExpenseF()
        Navigation.findNavController(view).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}