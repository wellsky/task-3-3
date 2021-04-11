package content

interface CrudService<E: Content> {
    var items: MutableList<E>
    var nextItemId: Long

    fun reset() {
        items = mutableListOf<E>()
        nextItemId = 0
    }

    fun add(entity: E): Long {
        entity.id = ++nextItemId
        this.items.add(entity)
        return nextItemId
    }

    fun delete(id: Long) {
        val item = this.getById(id)
        item.deleted = true
    }

    fun edit(entity: E)

    fun read(): MutableList<E> {
        return items
    }

    fun getById(id: Long, includingDeleted: Boolean = false): E {
        this.items.forEach {
            if (it.id == id) {
                if ((!it.deleted) || (includingDeleted)) {
                    return it
                }
            }
        }
        throw ContentNotFoundException("content.Content not found")
    }

    fun restore(id: Long) {
        val item = this.getById(id, true)
        item.deleted = false
    }
}