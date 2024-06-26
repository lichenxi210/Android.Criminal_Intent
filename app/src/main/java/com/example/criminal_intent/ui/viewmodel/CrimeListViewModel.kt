package com.example.criminal_intent.ui.viewmodel

import androidx.lifecycle.*
import com.example.criminal_intent.dao.CrimeRepository
import com.example.criminal_intent.model.Crime
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

//使用手册：只需observer监视暴露出来的两个属性（list是为列表准备的，detail那个是为详情页面准备的），要调用方法这里也封装了
//调试时需要清空数据库的话，只需要把下面的clearDatabase的注释去掉就行

class CrimeListViewModel(private val repository: CrimeRepository) : ViewModel() {
    private val _crimeListLiveData = MutableLiveData<List<Crime>>()
    private val _crimeDetail = MutableLiveData<Crime>()
    val crimeListLiveData: LiveData<List<Crime>> = _crimeListLiveData
    val crimeDetail: LiveData<Crime> = _crimeDetail

    init {
        viewModelScope.launch {
//            clearDatabase()
            if (repository.isDatabaseEmpty()) {
                initializeCrimes()
            }
            loadCrimes()
        }
    }

    private fun initializeCrimes() {
        val initialCrimes = mutableListOf<Crime>()
        val random = Random(System.currentTimeMillis()) // 用当前时间毫秒来初始化随机数生成器

        for (i in 0 until 10) {
            val randomDays = random.nextInt(1, 365) // 生成一个1到365之间的随机数
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -randomDays) // 从现在开始减去随机天数

            val crime = Crime(
                title = "Crime #$i details",
                date = calendar.time,
                isSolved = i % 2 == 0
            )
            crime.title += if (crime.isSolved) " - Solved" else " - Unsolved"
            initialCrimes += crime
        }

        viewModelScope.launch {
            initialCrimes.forEach { repository.addCrime(it) }
            loadCrimes()
        }
    }

    fun loadCrimes() = viewModelScope.launch {
        _crimeListLiveData.value = repository.getAllCrimes()
    }

    fun getCrime(crimeId: UUID) = viewModelScope.launch {
        _crimeDetail.value = repository.getCrime(crimeId)
    }

    fun addCrime(crime: Crime) = viewModelScope.launch {
        repository.addCrime(crime)
        loadCrimes()
    }

    fun updateCrime(crime: Crime) {
        viewModelScope.launch {
            repository.updateCrime(crime)
        }
    }

    fun clearDatabase(){
        viewModelScope.launch {
            repository.clearAllCrimes()
        }
    }
}