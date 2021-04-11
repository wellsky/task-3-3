package users

import content.CrudService
import users.User

object UserService: CrudService<User> {
    override var items: MutableList<User> = mutableListOf<User>()
    override var nextItemId: Long = 0

    override fun edit(user: User) {}
}