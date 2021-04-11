package chats

import messages.Message
import messages.MessageService
import content.CrudService
import users.User
import users.UserService

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

    fun getUserChats(userId: Long, onlyUnread: Boolean = false): MutableList<Chat> {
        var list: MutableList<Chat> = items.filter {
            (it.membersIds.contains(userId)) && (!it.deleted)
        }.toMutableList()
        return list
    }

    fun addUserToChat(chatId: Long, userId: Long) {
        val chat = getById(chatId)
        if (chat.membersIds.size < MAXIMUM_CHAT_MEMBERS) {
            chat.membersIds.add(userId)
        } else {
            // Ошибка, в чате максимальное число участников
        }
    }

    fun removeUserFromChat(chatId: Long, userId: Long) {
        val chat = getById(chatId)
        chat.membersIds.remove(userId)
    }

    fun getChatName(chatId: Long, userId: Long? = null): String {
        val chat = getById(chatId)
        if (userId != null) {
            if (chat.membersIds.count() == 2) {
                // Если количество участников = 2, то вместо названия чата возвращаем никнейм второго участника
                chat.membersIds.forEach {
                    if (it != userId) {
                        val user = UserService.getById(it)
                        return user.name
                    }
                }

            }
        }

        // Если количество участников больше 2, то возвращаем фактическое название чата
        return chat.name
    }

    fun getChatMessages(user: User, chatId: Long, count: Int? = null):  MutableList<Message> {
        // Возвращает сообщения от messages.MessageService,
        // Но начинает загрузку с первого непрочитанного и устанавливает прочитанным все до последнего полученного сообщения
        val chat = getById(chatId)
        val startAfterId = user.chatLastReadMessage.get(chat.id)
        val messages = MessageService.getChatMessages(chatId, startAfterId, count)

        // Устанавливаем прочитанным все, включая последнее загруженное сообщение
        user.chatLastReadMessage.set(chatId, messages.last().id)

        return messages
    }

    fun addMessage(message: Message) {
        val messageId = MessageService.add(message)
        if (message.chatId != null) {
           val chat = getById(message.chatId)
           chat.lastMessageId =  messageId
        }
    }

}