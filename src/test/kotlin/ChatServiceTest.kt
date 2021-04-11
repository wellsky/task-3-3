import org.junit.Assert
import org.junit.Test
import users.*
import messages.*
import chats.*

class ChatServiceTest {
    @Test
    fun addChatFactName() {
        // Создает чат и проверяет его фактическое имя
        UserService.reset()
        MessageService.reset()
        ChatService.reset()

        val user1 = UserService.getById(UserService.add(User("User1")))
        val user2 = UserService.getById(UserService.add(User("User2")))

        val chatId = ChatService.add(Chat(user1.id!!, "Chat 1"))
        ChatService.addUserToChat(chatId, user2.id!!)

        Assert.assertEquals("Chat 1", ChatService.getChatName(chatId));
    }

    @Test
    fun addChatUserName() {
        // Создает чат и проверяет его имя для второго пользователя
        // Должен быть никнейм первого пользователя
        UserService.reset()
        MessageService.reset()
        ChatService.reset()

        val user1 = UserService.getById(UserService.add(User("User1")))
        val user2 = UserService.getById(UserService.add(User("User2")))

        val chatId = ChatService.add(Chat(user1.id!!, "Chat 1"))
        ChatService.addUserToChat(chatId, user2.id!!)

        Assert.assertEquals("User1", ChatService.getChatName(chatId, user2.id));
    }

    @Test
    fun chatMembersLimit() {
        // Пробует добавить в чат больше 4-х пользователей
        UserService.reset()
        MessageService.reset()
        ChatService.reset()

        val user1 = UserService.getById(UserService.add(User("User1")))
        val chatId = ChatService.add(Chat(user1.id!!, "Chat 1"))

        ChatService.addUserToChat(chatId, UserService.add(User("User2")))
        ChatService.addUserToChat(chatId, UserService.add(User("User3")))
        ChatService.addUserToChat(chatId, UserService.add(User("User4")))
        ChatService.addUserToChat(chatId, UserService.add(User("User5")))

        val chat = ChatService.getById(chatId)

        Assert.assertEquals(4, chat.membersIds.size);
    }

    @Test
    fun userReadMessages() {
        // Проверяет, что после прочтения пользоватлем двух сообщений, следующие получит начиная с 3-го
        UserService.reset()
        MessageService.reset()
        ChatService.reset()

        val user1 = UserService.getById(UserService.add(User("User1")))
        val user2 = UserService.getById(UserService.add(User("User2")))

        val chatId = ChatService.add(Chat(user1.id!!, "Chat 1"))
        ChatService.addUserToChat(chatId, user2.id!!)

        ChatService.addMessage(Message(chatId, user1.id!!, 123, "First message text"))
        ChatService.addMessage(Message(chatId, user1.id!!, 123, "Second message text"))


        ChatService.getChatMessages(user2, chatId)

        // Второй чат, чтобы убедиться, что его сообщения не участвуют в фильтре
        val chat2Id = ChatService.add(Chat(user1.id!!, "Chat 2"))
        ChatService.addMessage(Message(chat2Id, user1.id!!, 123, "First message in chat 2"))
        ChatService.addMessage(Message(chat2Id, user1.id!!, 123, "Second message in chat 2"))

        ChatService.addMessage(Message(chatId, user1.id!!, 123, "Third message text"))
        val messages = ChatService.getChatMessages(user2, chatId)
        val message = messages.get(0)
        Assert.assertEquals("Third message text", message.text);
    }

    @Test
    fun userChatsCountTest() {
        // Создается 3 чата, добавляются оба пользователя, потом второй выходит из 1 чата
        // У него должно быть 2 чата в списке
        UserService.reset()
        MessageService.reset()
        ChatService.reset()

        val user1 = UserService.getById(UserService.add(User("User1")))
        val user2 = UserService.getById(UserService.add(User("User2")))

        val chatId = ChatService.add(Chat(user1.id!!, "Chat 1"))
        ChatService.addUserToChat(chatId, user2.id!!)
        val chat2Id = ChatService.add(Chat(user1.id!!, "Chat 2"))
        ChatService.addUserToChat(chat2Id, user2.id!!)
        val chat3Id = ChatService.add(Chat(user1.id!!, "Chat 3"))
        ChatService.addUserToChat(chat3Id, user2.id!!)
        ChatService.removeUserFromChat(chatId, user2.id!!)

        Assert.assertEquals(2, ChatService.getUserChats(user2.id!!).size);
    }
}