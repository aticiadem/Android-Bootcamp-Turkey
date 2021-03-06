package com.aa.harcamalarabt.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.adapter.ExpenseRecyclerAdapter
import com.aa.harcamalarabt.data.ExpenseDatabase
import com.aa.harcamalarabt.databinding.FragmentHomeBinding
import com.aa.harcamalarabt.model.ExpenseModel
import com.aa.harcamalarabt.viewmodel.HomeViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var currencyDataList: ArrayList<Double>
    private lateinit var expenseList: List<ExpenseModel>
    private lateinit var db: ExpenseDatabase
    private lateinit var adapter: ExpenseRecyclerAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = ExpenseDatabase.getDatabase(requireContext())
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        expenseList = db.expenseDao().readAllData()
        currencyDataList = ArrayList()
        sharedPreferences = requireActivity().getSharedPreferences("Name", Context.MODE_PRIVATE)

        workPlanner(sharedPreferences,expenseList,currencyDataList)

        binding.buttonTl.setOnClickListener {
            changeColor(it as Button)
            //viewModel.getData()
            changeLastClicked(sharedPreferences,1)
            changeCurrencyValue(1)
        }

        binding.buttonSterlin.setOnClickListener {
            changeColor(it as Button)
            //viewModel.getData()
            changeLastClicked(sharedPreferences,2)
            changeCurrencyValue(2)
        }

        binding.buttonDolar.setOnClickListener{
            changeColor(it as Button)
            //viewModel.getData()
            changeLastClicked(sharedPreferences,3)
            changeCurrencyValue(3)
        }

        binding.buttonEuro.setOnClickListener {
            changeColor(it as Button)
            //viewModel.getData()
            changeLastClicked(sharedPreferences,4)
            changeCurrencyValue(4)
        }

        binding.textViewUserName.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToChangeNameF()
            Navigation.findNavController(it).navigate(action)
        }

        binding.fab.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddExpenseF()
            Navigation.findNavController(it).navigate(action)
        }

    }

    private fun changeLastClicked(sharedPreferences: SharedPreferences, lastClickedItem: Int){
        val editor = sharedPreferences.edit()
        editor.putInt("lastClickedItem",lastClickedItem)
        editor.apply()
    }

    @SuppressLint("SetTextI18n")
    private fun changeCurrencyValue(currencyType: Int){
        when(currencyType){
            1 -> {
                val adapter = ExpenseRecyclerAdapter(1,currencyDataList)
                adapter.setData(db.expenseDao().readAllData())
                binding.recyclerView.adapter = adapter
                val totalPrice = calculator(1,currencyDataList,expenseList)
                binding.textViewYourExpense.text = "${DecimalFormat("#.#").format(totalPrice)} ???"
            }
            2 -> {
                val adapter = ExpenseRecyclerAdapter(2,currencyDataList)
                adapter.setData(db.expenseDao().readAllData())
                binding.recyclerView.adapter = adapter
                val totalPrice = calculator(2,currencyDataList,expenseList)
                binding.textViewYourExpense.text = "${DecimalFormat("#.#").format(totalPrice)} ??"
            }
            3 -> {
                val adapter = ExpenseRecyclerAdapter(3,currencyDataList)
                adapter.setData(db.expenseDao().readAllData())
                binding.recyclerView.adapter = adapter
                val totalPrice = calculator(3,currencyDataList,expenseList)
                binding.textViewYourExpense.text = "${DecimalFormat("#.#").format(totalPrice)} $"
            }
            4 -> {
                val adapter = ExpenseRecyclerAdapter(4,currencyDataList)
                adapter.setData(db.expenseDao().readAllData())
                binding.recyclerView.adapter = adapter
                val totalPrice = calculator(4,currencyDataList,expenseList)
                binding.textViewYourExpense.text = "${DecimalFormat("#.#").format(totalPrice)} ???"
            }
        }
    }

    private fun calculator(currencyType: Int, list: ArrayList<Double>, expenseList: List<ExpenseModel>): Double{
        var priceValue = 0.0
        for(i in expenseList){
            when(currencyType){
                1 -> { // Tl ye basilmis
                    when(i.currencyType){ // veritabanindaki birimlerin tl donusumu
                        1 -> { // TL
                            priceValue += i.priceValue
                        }
                        2 -> { // Sterlin tl ceviri
                            // 1 sterlin kac euro? -> x
                            val x = 1/list[1]
                            // x euro kac tl ?
                            val value = x*list[0]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        3 -> { // Dolar
                            val x = 1/list[2]
                            val value = x*list[0]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        4 -> { // Euro
                            val tl_1_kac_euro = 1*list[0]
                            val y = i.priceValue * tl_1_kac_euro
                            priceValue += y
                        }
                    }
                }
                2 -> { // Sterlin e basilmis
                    when(i.currencyType){
                        1 -> { // TL
                            val x = 1/list[0]
                            val value = x * list[1]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        2 -> { // Sterlin
                            priceValue += i.priceValue
                        }
                        3 -> { // Dolar
                            val x = 1/list[2]
                            val value = x * list[1]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        4 -> { // Euro
                            val tl_1_kac_euro = 1*list[1]
                            val y = i.priceValue * tl_1_kac_euro
                            priceValue += y
                        }
                    }
                }
                3 -> { // Dolar a basilmis
                    when(i.currencyType){
                        1 -> { // TL
                            val x = 1/list[0]
                            val value = x * list[2]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        2 -> { // Sterlin
                            val x = 1/list[1]
                            val value = x * list[2]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        3 -> { // Dolar
                            priceValue += i.priceValue
                        }
                        4 -> { // Euro
                            val tl_1_kac_euro = 1*list[2]
                            val y = i.priceValue * tl_1_kac_euro
                            priceValue += y
                        }
                    }
                }
                4 -> { // Euro yaa basilmis
                    when(i.currencyType){
                        1 -> { // TL
                            val x = 1/list[0]
                            val value = x * list[3]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        2 -> { // Sterlin
                            val x = 1/list[1]
                            val value = x * list[3]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        3 -> { // Dolar
                            val x = 1/list[2]
                            val value = x * list[3]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                        4 -> { // Euro
                            priceValue += i.priceValue
                        }
                    }
                }
            }
        }
        return priceValue
    }

    private fun changeColor(button: Button){
        binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        button.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
    }

    private fun observeLiveData(sharedPreferences: SharedPreferences){
        viewModel.data.observe(viewLifecycleOwner, { data ->
            data.let {
                val editor = sharedPreferences.edit()
                editor.putFloat("tl",it.rates.tRY.toFloat())
                editor.putFloat("sterlin",it.rates.gBP.toFloat())
                editor.putFloat("dolar",it.rates.uSD.toFloat())
                editor.putFloat("euro",it.rates.eUR.toFloat())
                editor.apply()
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.loadingMessage.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it){
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it){
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    Snackbar.make(binding.root,"G??ncel Veriler ??ekilemedi.", Snackbar.LENGTH_SHORT)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .setTextColor(Color.WHITE)
                            .setBackgroundTint(Color.DKGRAY)
                            .setAction("Tamam"){}
                            .setActionTextColor(Color.RED)
                            .show()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun workPlanner(sharedPreferences: SharedPreferences,
                            expenseList: List<ExpenseModel>,
                            currencyDataList: ArrayList<Double>) {
        val x = sharedPreferences.getInt("number",0)
        if(x == 0){

            val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

            if (isConnected){
                viewModel.getData()
                observeLiveData(sharedPreferences)
            } else {
                Snackbar.make(binding.root,"??nternet Ba??lant??s?? Yok", Snackbar.LENGTH_SHORT)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .setTextColor(Color.YELLOW)
                        .setBackgroundTint(Color.DKGRAY)
                        .setAction("Tamam"){}
                        .setActionTextColor(Color.RED)
                        .show()
            }

            val name = sharedPreferences.getString("name","??sim Giriniz")
            val lastClickedItem = sharedPreferences.getInt("lastClickedItem",1)
            val dataTl = sharedPreferences.getFloat("tl", 9.97F)
            val dataSterlin = sharedPreferences.getFloat("sterlin", 0.87F)
            val dataDolar = sharedPreferences.getFloat("dolar", 1.20F)
            val dataEuro = sharedPreferences.getFloat("euro", 1F)

            currencyDataList.clear()
            currencyDataList.add(dataTl.toDouble())
            currencyDataList.add(dataSterlin.toDouble())
            currencyDataList.add(dataDolar.toDouble())
            currencyDataList.add(dataEuro.toDouble())

            when(sharedPreferences.getInt("gender",3)){
                1 -> binding.textViewUserName.text = "$name Bey"
                2 -> binding.textViewUserName.text = "$name Han??m"
                3 -> binding.textViewUserName.text = name
            }

            when(lastClickedItem){
                1 -> binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                2 -> binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
                3 -> binding.buttonDolar.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
                else -> binding.buttonEuro.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
            }

            adapter = ExpenseRecyclerAdapter(lastClickedItem,currencyDataList)
            adapter.setData(expenseList)
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)

            changeCurrencyValue(lastClickedItem)

            val editor = sharedPreferences.edit()
            editor.putInt("number",1)
            editor.apply()

        } else {

            val name = sharedPreferences.getString("name","??sim Giriniz")
            val lastClickedItem = sharedPreferences.getInt("lastClickedItem",1)
            val dataTl = sharedPreferences.getFloat("tl", 9.97F)
            val dataSterlin = sharedPreferences.getFloat("sterlin", 0.87F)
            val dataDolar = sharedPreferences.getFloat("dolar", 1.20F)
            val dataEuro = sharedPreferences.getFloat("euro", 1F)

            currencyDataList.clear()
            currencyDataList.add(dataTl.toDouble())
            currencyDataList.add(dataSterlin.toDouble())
            currencyDataList.add(dataDolar.toDouble())
            currencyDataList.add(dataEuro.toDouble())

            adapter = ExpenseRecyclerAdapter(1,currencyDataList)
            adapter.setData(expenseList)
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)

            when(sharedPreferences.getInt("gender",3)){
                1 -> binding.textViewUserName.text = "$name Bey"
                2 -> binding.textViewUserName.text = "$name Han??m"
                3 -> binding.textViewUserName.text = name
            }

            changeCurrencyValue(lastClickedItem)

            when(lastClickedItem){
                1 -> binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                2 -> binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
                3 -> binding.buttonDolar.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
                else -> binding.buttonEuro.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}