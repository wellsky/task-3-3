package chats

import content.Content

data class Chat (
    val ownerId: Long,
    var name: String,
    var membersIds: MutableList<Long> = mutableListOf(),

    var lastMessageId: Long? = null,

    override var id: Long? = null,
    override var deleted: Boolean = false
): Content(id)
