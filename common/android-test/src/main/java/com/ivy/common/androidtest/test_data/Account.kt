package com.ivy.common.androidtest.test_data

import com.ivy.core.persistence.IvyWalletCoreDb
import com.ivy.core.persistence.entity.account.AccountEntity
import com.ivy.core.persistence.entity.trn.TransactionEntity
import com.ivy.data.SyncState
import com.ivy.data.account.AccountState
import java.time.Instant
import java.util.UUID

fun accountEntity(): AccountEntity {
    return AccountEntity(
        id = UUID.randomUUID().toString(),
        name = "Test account",
        currency = "EUR",
        color = 0x00000,
        icon = null,
        folderId = null,
        orderNum = 1.0,
        excluded = false,
        state = AccountState.Default,
        sync = SyncState.Syncing,
        lastUpdated = Instant.now()
    )
}

suspend fun IvyWalletCoreDb.saveAccountWithTransactions(
    accountEntity: AccountEntity = accountEntity(),
    transactions: List<TransactionEntity> = listOf(transactionEntity())
) {
    accountDao().save(listOf(accountEntity))

    val transactionsWithAccount = transactions.map {
        it.copy(
            accountId = accountEntity.id
        )
    }
    transactionsWithAccount.forEach {
        trnDao().save(saveTrnData(it))
    }
}
