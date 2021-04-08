data class User (
    val id: Long,
    var name: String,
    var chatLastReadMessage: HashMap<Long, Long> = HashMap<Long, Long>() // К каждому id-чата привязывается id последнего прочитанного сообщения
)