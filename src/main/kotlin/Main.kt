import chats.ChatService

import users.*
import messages.*
import chats.*

fun main() {
    val user1 = UserService.getById(UserService.add(User("User1")))
    val user2 = UserService.getById(UserService.add(User("User2")))
    val user3 = UserService.getById(UserService.add(User("User3")))
    val user4 = UserService.getById(UserService.add(User("User4")))

    val privateChatId = ChatService.add(Chat(user1.id!!, "Chat 1"))
    val publicChatId = ChatService.add(Chat(user1.id!!, "Chat 1"))

    ChatService.addUserToChat(privateChatId, user2.id!!)

    ChatService.addUserToChat(publicChatId, user2.id!!)
    ChatService.addUserToChat(publicChatId, user3.id!!)
    ChatService.addUserToChat(publicChatId, user4.id!!)

    ChatService.addMessage(Message(privateChatId, user1.id!!, 123, "Hello from user1!"))
    ChatService.addMessage(Message(privateChatId, user2.id!!, 123, "Hi from user2!"))

    ChatService.addMessage(Message(publicChatId, user1.id!!, 123, "Hi all from user1!"))
    ChatService.addMessage(Message(publicChatId, user2.id!!, 123, "Hi all from user2!"))
    ChatService.addMessage(Message(publicChatId, user4.id!!, 123, "Hi all from user4!"))



    println(ChatService.getChatMessages(user1, publicChatId))

    val message = Message(publicChatId, user4.id!!, 123, "This message well be deleted")
    ChatService.addMessage(message)

    MessageService.delete(message.id!!)

    ChatService.addMessage(Message(publicChatId, user4.id!!, 123, "Whats up?"))


    println(ChatService.getChatMessages(user1, publicChatId))
    //chats.ChatService.removeUserFromChat(chatId, user1.id)

    //println(chats.ChatService.getChatName(chatId))

    //val chats = chats.ChatService.getUserChats(user1.id)
    //println(chats)



}