fun main() {
    val user1 = User(11, "user 1")
    val user2 = User(22, "User 2")
    val user3 = User(33, "User 3")

    val chatId = ChatService.add(Chat(user1.id, "Chat 1"))

    ChatService.addUserToChat(chatId, user2.id)
    ChatService.addUserToChat(chatId, user3.id)
    //ChatService.removeUserFromChat(chatId, user1.id)

    //println(ChatService.getChatName(chatId))

    //val chats = ChatService.getUserChats(user1.id)
    //println(chats)

    println(ChatService.getById(chatId).membersIds)

}