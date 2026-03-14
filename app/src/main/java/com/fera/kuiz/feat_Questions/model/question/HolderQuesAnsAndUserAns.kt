package com.fera.kuiz.feat_Questions.model.question

import android.os.Parcel
import android.os.Parcelable
import com.fera.hymn.utils.enumeration.EnumDataInfo


data class HolderQuesAnsAndUserAns(
    var pkCategoryId: Long,
    val iconFilePath: String?,
    val noteFilePath: String?,
    val totalQuestions: Int,
    val lastQuestionTakenId: Long,
    val lastQuestionTakenNo: Int,

    var pkQuestionId: Long,
    var questionNo: Int,
    var questionType: String?,
    var question: String?,
    var difficulty: Int,
    var dateAdded: Long,
    var dataStatus: Int,

    var pkAnswerId: Long,
    var answer: String?,

    var pkUserAnswerId: Long,
    var answerUser: String?,
    var isCorrect: Boolean,
    var qStartTakenDate: Long
) : Parcelable {

    constructor() : this(
        pkCategoryId = 0L,
        iconFilePath = null,
        noteFilePath = null,
        totalQuestions = 0,
        lastQuestionTakenId = 0L,
        lastQuestionTakenNo = EnumDataInfo.NONE.info,

        pkQuestionId = 0L,
        questionNo = 0,
        questionType = "",
        question = "",
        difficulty = 0,
        dateAdded = 0L,
        dataStatus = EnumDataInfo.NONE.info,

        pkAnswerId = 0L,
        answer = "",

        pkUserAnswerId = 0L,
        answerUser = "",
        isCorrect = false,
        qStartTakenDate = 0L
    )

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),

        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong()
    )

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HolderQuesAnsAndUserAns> {
        override fun createFromParcel(parcel: Parcel): HolderQuesAnsAndUserAns {
            return HolderQuesAnsAndUserAns(parcel)
        }

        override fun newArray(size: Int): Array<HolderQuesAnsAndUserAns?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }
}
