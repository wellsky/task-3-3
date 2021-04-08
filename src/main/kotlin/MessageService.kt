object MessageService: CrudService<Message> {
    private const val MAXIMUM_CHAT_MEMBERS = 4

    override var items: MutableList<Message> = mutableListOf<Message>()
    override var nextItemId: Long = 0

    override fun edit(message: Message) {}

    override fun add(message: Message): Long {
        val messageId = super.add(message)
        val message = getById(messageId)
        if (message.chatId != null) {
            ChatService.registerMessage(message)
        }
        return messageId
    }

    fun getChatMessages(chatId: Long, startAfterId: Long, count: Int = 10): MutableList<Message> {
        var list: MutableList<Message> = mutableListOf<Message>()

        this.items.forEach {
            if ((it.chatId == chatId) && (!it.deleted)) {
                list.add(it)
            }
        }

        // !! this.items.subList()

        return list
    }
}