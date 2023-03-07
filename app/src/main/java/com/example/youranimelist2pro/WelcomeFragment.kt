/*
class WelcomeFragment : Fragment() {
 //   private lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        val name = requireArguments().getString("user_name")
        val email = requireArguments().getString("user_email")
        binding.apply {
        /*    tvWelcomeName.text = name
            tvWelcomeEmail.text = email
            btnViewTerms.setOnClickListener {
             it.findNavController().navigate(R.id.action_welcomeFragment_to_termsFragment)
            }*/
        }
        return binding.root
    }


    <ImageView
        android:id="@+id/imageView"
        android:layout_width="240dp"
        android:layout_height="253dp"
        android:src="@drawable/hutao"
        app:layout_constraintBottom_toTopOf="@+id/bottomNavigationView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.497"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/title" />

    <TextView
        android:id="@+id/title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:fontFamily="monospace"
        android:gravity="center"
        android:text="Welcome! what would you like to do today?"
        android:textSize="34sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toTopOf="@+id/imageView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.501"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
 */


