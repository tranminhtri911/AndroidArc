package n.com.myapplication.screen.main.userPaging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_paging.view.*
import n.com.myapplication.R
import n.com.myapplication.data.model.User


class PagingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_paging, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getParcelable<User>(USER_EXTRA)

        view.label.text = user?.fullName

        view.sublabel.text = user?.name
    }

    companion object {

        private const val USER_EXTRA = "USER_EXTRA"

        fun newInstance(user: User) = PagingFragment().apply {
            val bundle = Bundle().apply { putParcelable(USER_EXTRA, user) }
            arguments = bundle
        }
    }
}

