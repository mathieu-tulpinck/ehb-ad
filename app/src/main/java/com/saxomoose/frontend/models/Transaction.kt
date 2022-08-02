package com.saxomoose.frontend.models

data class Transaction(
    val id: Int,
    val uploaded: Boolean,
    val items: List<TransactionItem>
) {}