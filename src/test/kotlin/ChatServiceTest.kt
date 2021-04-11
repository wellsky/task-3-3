import org.junit.Assert
import org.junit.Test
import users.*
import messages.*
import chats.*

class ChatServiceTest {
    @Test
    fun addChatFactName() {
        // Создает чат и выводит его фактическое имя
        UserService.reset()
        MessageService.reset()
        ChatService.reset()

        val user1 = UserService.getById(UserService.add(User("User1")))
        val user2 = UserService.getById(UserService.add(User("User2")))

        val privateChatId = ChatService.add(Chat(user1.id!!, "Chat 1"))
        ChatService.addUserToChat(privateChatId, user2.id!!)

        Assert.assertEquals("Chat 1", ChatService.getChatName(privateChatId));
    }

    @Test
    fun addChatUserName() {
        // Создает чат и выводит его имя для второго пользователя
        // Должен быть никнейм первого пользователя
        UserService.reset()
        MessageService.reset()
        ChatService.reset()

        val user1 = UserService.getById(UserService.add(User("User1")))
        val user2 = UserService.getById(UserService.add(User("User2")))

        val privateChatId = ChatService.add(Chat(user1.id!!, "Chat 1"))
        ChatService.addUserToChat(privateChatId, user2.id!!)

        Assert.assertEquals("User1", ChatService.getChatName(privateChatId, user2.id));
    }

    @Test
    fun chatMembersLimit() {
        // Пробует добавить в чат больше 4-х пользователей
        UserService.reset()
        MessageService.reset()
        ChatService.reset()

        val user1 = UserService.getById(UserService.add(User("User1")))
        val publicChatId = ChatService.add(Chat(user1.id!!, "Chat 1"))

        ChatService.addUserToChat(publicChatId, UserService.add(User("User2")))
        ChatService.addUserToChat(publicChatId, UserService.add(User("User3")))
        ChatService.addUserToChat(publicChatId, UserService.add(User("User4")))
        ChatService.addUserToChat(publicChatId, UserService.add(User("User5")))

        val chat = ChatService.getById(publicChatId)

        Assert.assertEquals(4, chat.membersIds.size);
    }
}