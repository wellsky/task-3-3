data class Message (
    val id: Int, // идентификатор сообщеия
    val chatId: Int, // идентификатор чата
    val authorId: Int, // идентификатор автора
    val date: Int, // дата создания сообщения в формате Unixtime.
    var text: String, // текст сообщения
    var deleted: Boolean = false // Флаг удаленного сообщения
)