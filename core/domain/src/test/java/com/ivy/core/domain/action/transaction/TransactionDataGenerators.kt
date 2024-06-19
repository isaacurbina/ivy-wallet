package com.ivy.core.domain.action.transaction

import com.ivy.data.Sync
import com.ivy.data.SyncState
import com.ivy.data.Value
import com.ivy.data.account.Account
import com.ivy.data.account.AccountState
import com.ivy.data.attachment.Attachment
import com.ivy.data.attachment.AttachmentSource
import com.ivy.data.attachment.AttachmentType
import com.ivy.data.tag.Tag
import com.ivy.data.tag.TagState
import com.ivy.data.transaction.Transaction
import com.ivy.data.transaction.TransactionType
import com.ivy.data.transaction.TrnMetadata
import com.ivy.data.transaction.TrnPurpose
import com.ivy.data.transaction.TrnState
import com.ivy.data.transaction.TrnTime
import java.time.LocalDateTime
import java.util.UUID

fun account() = Account(
    id = UUID.randomUUID(),
    name = "Test Account",
    currency = "EUR",
    color = 0x00f15e,
    icon = null,
    excluded = false,
    folderId = null,
    orderNum = 1.0,
    state = AccountState.Default,
    sync = Sync(
        state = SyncState.Syncing,
        lastUpdated = LocalDateTime.now()
    )
)

fun tag() = Tag(
    id = UUID.randomUUID().toString(),
    color = 0x00f15e,
    name = "Test tag",
    orderNum = 1.0,
    state = TagState.Default,
    sync = Sync(
        state = SyncState.Syncing,
        lastUpdated = LocalDateTime.now()
    )
)

fun attachment(associatedId: String) = Attachment(
    id = UUID.randomUUID().toString(),
    associatedId = associatedId,
    uri = "test",
    source = AttachmentSource.Local,
    filename = null,
    type = AttachmentType.Image,
    sync = Sync(
        state = SyncState.Syncing,
        lastUpdated = LocalDateTime.now()
    )
)

fun transaction(id: UUID, account: Account) = Transaction(
    id = id,
    account = account,
    type = TransactionType.Expense,
    value = Value(
        amount = 50.0,
        currency = "EUR"
    ),
    category = null,
    time = TrnTime.Actual(LocalDateTime.now()),
    title = "Test transaction",
    description = null,
    state = TrnState.Default,
    purpose = TrnPurpose.Fee,
    tags = emptyList(),
    attachments = emptyList(),
    metadata = TrnMetadata(
        recurringRuleId = null,
        loanId = null,
        loanRecordId = null
    ),
    sync = Sync(
        state = SyncState.Syncing,
        lastUpdated = LocalDateTime.now()
    )
)
