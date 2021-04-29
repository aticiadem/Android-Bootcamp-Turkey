package com.aa.harcamalarabt.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.adapter.ExpenseRecyclerAdapter
import com.aa.harcamalarabt.data.ExpenseDatabase
import com.aa.harcamalarabt.databinding.FragmentHomeBinding
import com.aa.harcamalarabt.model.CurrencyModel
import com.aa.harcamalarabt.model.ExpenseModel
import com.aa.harcamalarabt.service.CurrencyAPIService
import com.aa.harcamalarabt.viewmodel.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
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

        binding.buttonManat.setOnClickListener {
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
                binding.textViewHarcama.text = "${DecimalFormat("#.#").format(totalPrice)} TL"
            }
            2 -> {
                val adapter = ExpenseRecyclerAdapter(2,currencyDataList)
                adapter.setData(db.expenseDao().readAllData())
                binding.recyclerView.adapter = adapter
                val totalPrice = calculator(2,currencyDataList,expenseList)
                binding.textViewHarcama.text = "${DecimalFormat("#.#").format(totalPrice)} Sterlin"
            }
            3 -> {
                val adapter = ExpenseRecyclerAdapter(3,currencyDataList)
                adapter.setData(db.expenseDao().readAllData())
                binding.recyclerView.adapter = adapter
                val totalPrice = calculator(3,currencyDataList,expenseList)
                binding.textViewHarcama.text = "${DecimalFormat("#.#").format(totalPrice)} Dolar"
            }
            4 -> {
                val adapter = ExpenseRecyclerAdapter(4,currencyDataList)
                adapter.setData(db.expenseDao().readAllData())
                binding.recyclerView.adapter = adapter
                val totalPrice = calculator(4,currencyDataList,expenseList)
                binding.textViewHarcama.text = "${DecimalFormat("#.#").format(totalPrice)} Manat"
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
                        4 -> { // Manat
                            val x = 1/list[3]
                            val value = x*list[0]
                            val total = i.priceValue * value
                            priceValue += total
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
                        4 -> { // Manat
                            val x = 1/list[3]
                            val value = x*list[1]
                            val total = i.priceValue * value
                            priceValue += total
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
                        4 -> { // Manat
                            val x = 1/list[3]
                            val value = x * list[2]
                            val total = i.priceValue * value
                            priceValue += total
                        }
                    }
                }
                4 -> { // Manat a basilmis
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
                        4 -> { // Manat
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
        binding.buttonManat.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        button.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
    }

    private fun observeLiveData(sharedPreferences: SharedPreferences){
        viewModel.data.observe(viewLifecycleOwner, { data ->
            data.let {
                val editor = sharedPreferences.edit()
                editor.putFloat("tl",it.rates.tRY.toFloat())
                editor.putFloat("sterlin",it.rates.gBP.toFloat())
                editor.putFloat("dolar",it.rates.uSD.toFloat())
                editor.putFloat("manat",it.rates.aZN.toFloat())
                editor.apply()
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                println("HomeFragment Veriler Alindi")
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
                    // Error true
                    //Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    println("HomeFragmentError")
                }
                /*else{
                    // Error false
                }*/
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun workPlanner(sharedPreferences: SharedPreferences,
                            expenseList: List<ExpenseModel>,
                            currencyDataList: ArrayList<Double>) {
        val x = sharedPreferences.getInt("number",0)
        if(x == 0){
            // one time
            viewModel.getData()
            observeLiveData(sharedPreferences)

            val name = sharedPreferences.getString("name","İsim Giriniz")
            val lastClickedItem = sharedPreferences.getInt("lastClickedItem",1)
            val dataTl = sharedPreferences.getFloat("tl", 23.3F)
            val dataSterlin = sharedPreferences.getFloat("sterlin", 100.0F)
            val dataDolar = sharedPreferences.getFloat("dolar", 1.4F)
            val dataManat = sharedPreferences.getFloat("manat", 54.3F)

            currencyDataList.clear()
            currencyDataList.add(dataTl.toDouble())
            currencyDataList.add(dataSterlin.toDouble())
            currencyDataList.add(dataDolar.toDouble())
            currencyDataList.add(dataManat.toDouble())

            when(sharedPreferences.getInt("gender",3)){
                1 -> binding.textViewUserName.text = "$name Bey"
                2 -> binding.textViewUserName.text = "$name Hanım"
                3 -> binding.textViewUserName.text = name
            }

            when(lastClickedItem){
                1 -> binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                2 -> binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
                3 -> binding.buttonDolar.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
                else -> binding.buttonManat.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
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

            val name = sharedPreferences.getString("name","İsim Giriniz")
            val lastClickedItem = sharedPreferences.getInt("lastClickedItem",1)
            val dataTl = sharedPreferences.getFloat("tl", 23.3F)
            val dataSterlin = sharedPreferences.getFloat("sterlin", 100.0F)
            val dataDolar = sharedPreferences.getFloat("dolar", 1.4F)
            val dataManat = sharedPreferences.getFloat("manat", 54.3F)

            currencyDataList.clear()
            currencyDataList.add(dataTl.toDouble())
            currencyDataList.add(dataSterlin.toDouble())
            currencyDataList.add(dataDolar.toDouble())
            currencyDataList.add(dataManat.toDouble())

            adapter = ExpenseRecyclerAdapter(1,currencyDataList)
            adapter.setData(expenseList)
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)

            when(sharedPreferences.getInt("gender",3)){
                1 -> binding.textViewUserName.text = "$name Bey"
                2 -> binding.textViewUserName.text = "$name Hanım"
                3 -> binding.textViewUserName.text = name
            }

            changeCurrencyValue(lastClickedItem)

            when(lastClickedItem){
                1 -> binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                2 -> binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
                3 -> binding.buttonDolar.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
                else -> binding.buttonManat.setTextColor(ContextCompat.getColor(requireActivity(),R.color.orange))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}