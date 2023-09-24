package com.example.kleine.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RewardHistoryDao {

    @Insert
    suspend fun insertRewardHistory(rewardHistory: RewardHistory)

    @Query("SELECT * FROM reward_history ORDER BY redeemedDate DESC")
    suspend fun getAllRewardHistory(): List<RewardHistory>
}
