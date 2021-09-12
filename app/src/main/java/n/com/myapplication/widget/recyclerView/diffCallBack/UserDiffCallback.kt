package n.com.myapplication.widget.recyclerView.diffCallBack

/*
class UserDiffCallback(private val olds: MutableList<User>,
        private val news: MutableList<User>) : BaseDiffCallback<User>(olds, news) {

    override fun areItemsSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldItem: User? = olds[oldPosition]
        val newItem: User? = news[newPosition]
        return oldItem?.id == newItem?.id && oldItem?.name == newItem?.name
    }

    override fun areContentsSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldItem: User? = olds[newPosition]
        val newItem: User? = news[newPosition]
        return oldItem == newItem
    }

}
*/
