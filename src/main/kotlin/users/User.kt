package users

import content.Content

data class User (
    var name: String,
    var chatLastReadMessage: HashMap<Long, Long?> = HashMap<Long, Long?>(), // К каждому id-чата привязывается id последнего прочитанного сообщения

    override var id: Long? = null,
    override var deleted: Boolean = false
): Content()