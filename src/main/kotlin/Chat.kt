data class Chat (
    val ownerId: Int,
    var name: String,
    var membersIds: MutableList<Int> = mutableListOf(),
    
    override var id: Long? = null,
    override var deleted: Boolean = false
): Content(id)
