package com.fera.kuiz.feat_userAuth.model

interface InterfaceUserRegistration {
    suspend fun insertUserRegistration(userRegistration: TblUserRegistration): Long

    suspend fun updateUserRegistration(userRegistration: TblUserRegistration)

    suspend fun deleteUserRegistration(pkUserRegistrationId: Long)
}