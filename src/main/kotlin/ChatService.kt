object ChatService: CrudService<Chat> {
    private const val MAXIMUM_CHAT_MEMBERS = 4

    override var items: MutableList<Chat> = mutableListOf<Chat>()
    override var nextItemId: Long = 0

    override fun add(chat: Chat): Long {
        val chatId = super.add(chat)
        addUserToChat(chatId, chat.ownerId)
        return chatId
    }

    override fun edit(chat: Chat) {

    }

    fun getUserChats(userId: Int, onlyUnread: Boolean = false): MutableList<Chat> {
        var list: MutableList<Chat> = mutableListOf<Chat>()

        this.items.forEach {
            if ((it.membersIds.contains(userId)) && (!it.deleted)) {
                list.add(it)
            }
        }
        return list
    }

    fun addUserToChat(chatId: Long, userId: Int) {
        val chat = getById(chatId)
        chat.membersIds.add(userId)
    }

    fun removeUserFromChat(chatId: Long, userId: Int) {
        val chat = getById(chatId)
        chat.membersIds.remove(userId)
    }

    fun getChatName(chatId: Long, userId: Int? = null): String {
        val chat = getById(chatId)
        if (chat.membersIds.count() == 2) {
            // Если количество участников = 2, то вместо названия чата возвращаем никнейм второго участника
            return "interlocutor"
        } else {
            // Если количество участников больше 2, то возвращаем фактическое название чата
            return chat.name
        }
    }

    fun getChatMessages(userId: Int, chatId: Int, firstMessageid: Int, count: Int) {

    }
}