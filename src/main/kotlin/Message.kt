data class Message (
    val chatId: Long, // идентификатор чата
    val authorId: Long, // идентификатор автора
    val date: Int, // дата создания сообщения в формате Unixtime.
    var text: String, // текст сообщения

    override var id: Long? = null,
    override var deleted: Boolean = false
): Content()