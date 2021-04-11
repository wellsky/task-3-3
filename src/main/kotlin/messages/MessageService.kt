package messages

import content.CrudService

object MessageService: CrudService<Message> {
    private const val MAXIMUM_CHAT_MEMBERS = 4

    override var items: MutableList<Message> = mutableListOf<Message>()
    override var nextItemId: Long = 0

    override fun edit(message: Message) {}

    override fun add(message: Message): Long {
        val messageId = super.add(message)
        val message = getById(messageId)
        return messageId
    }

    fun getChatMessages(chatId: Long, startAfterId: Long? = null, count: Int? = null): MutableList<Message> {
        var list: MutableList<Message> = mutableListOf<Message>()

        items.forEach {
            if ((it.chatId == chatId) && (!it.deleted)) {
                list.add(it)
            }
        }

        var fromIndex = 0
        var toIndex = list.size

        if (startAfterId != null) {
            val startAfterMessage = this.getById(startAfterId) // Выводить все после этого сообщения
            fromIndex = items.indexOf(startAfterMessage) - 1
        }

        // Если указано количество запрашиваемых сообзений, то меняем toIndex
        if (count != null) {
            toIndex = fromIndex + count
        }

        // Нельзя выходить за пределы общего количества сообщений
        if (toIndex > list.size) toIndex = list.size

        // Достаем подмножество, если указан хоть один из необходимых параметров
        if ((startAfterId != null) || (count != null))
            list = list.subList(fromIndex, toIndex)

        return list
    }
}