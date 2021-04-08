object ChatService: CrudService<Chat> {
    private const val MAXIMUM_CHAT_MEMBERS = 4

    override var items: MutableList<Chat> = mutableListOf<Chat>()
    override var nextItemId: Long = 0

    override fun edit(chat: Chat) {}

    override fun add(chat: Chat): Long {
        val chatId = super.add(chat)
        addUserToChat(chatId, chat.ownerId)
        return chatId
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
        if (chat.membersIds.size < MAXIMUM_CHAT_MEMBERS) {
            chat.membersIds.add(userId)
        } else {
            // Ошибка, в чате максимальное число участников
        }
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

    fun getChatMessages(user:User, chatId: Long, lastReadId: Long, count: Int) {

        user.chatLastReadMessage.set(chatId, 1) // Устанавливаем все прочитанным
    }

    fun registerMessage(message: Message) {
        val messageId = MessageService.add(message)
    }

}