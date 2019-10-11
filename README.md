# JACS MVVM
A library providing a base Fragment, ViewModel and various tools to make creating a project with MVVM and data binding easier. This has been designed for one-activity apps.
 
## Adding New Fragments
When you add a new fragment to the project, have it extend JACSFragment or create your own BaseFragment that extends JACSFragment and have your new fragment extend your BaseFragment. 
In the onCrease method:
- Set 'layoutID' to the layout resource you want to use for the fragment
- If you need to set up your views, override `setUpViews()` and put your view set-up code there
 
## Enabling ViewModels and Data Binding in Fragments
- Extend the Fragment with 'JACSFragment<FragmentNameBinding, FragmentNameViewModel>'
- Inside the onCreate method do 'viewModelClass = FragmentNameViewModel.class'
- Override the method `setUpViewModel`
- Inside this method do 'viewBinding.setDataBindingNameViewModel(viewModel)'
- If needed, override `init()` and add setup code there
 
The JACSFragment will handle creating the ViewModel of the given class and set up the data binding for your view. You will now be able to access your ViewModel with the variable 'viewModel'.
 
## Creating A ViewModel
- Extend the class with JACSViewModel or create your own BaseViewModel that extends JACSViewModel and extend new ViewModels by BaseViewModel
- Create the public contructor to match the super
- Id needed, overrive `init()` and add setup code there
 
## Navigation
To call a Navigation action from a ViewModel that extends JACSViewModel:
- Ensure the fragment that's using the ViewModel extend JACSFragment
- Inside the ViewModel create a function for navigation (e.g public void goToSecondFragment())
- Handle any logic you need first, then call `performAction(Integer, Bundle)` passing the integer (R.id.action_firstFragment_to_secondFragment) and either a Bundle or null
- If you passed a bundle, you can get the contents of the bundle inside the new fragment with `getBundleData()`

## Recycler Views with Data Binding
To use the JACSRecyclerAdapter with a RecyclerView:
- Ensure your RecyclerViewAdapter extends JACSRecyclerViewAdapter<DataType, YourViewHolder> (DataType is whatever your adapter is exptecting a list of e.g UserModel)
- Override the `setData` method and set the data for data binding (this.items = data)
- Override the `onCreateViewHolder` method to inflate your ViewHolder (e.g new YourViewHolder(inflate(R.layout.your_view_holder, parent, false))
- Create your ViewHolder and extend it fromfrom JACSViewHolder<DataType>
- Override the `onBind` method and set the data for the ViewHolder (e.g textView.setText(UserModel.name)
- In the recyclerView's xml bind the data (app:data="yourViewModel.data")
- If needed add the onClickListener by calling adapter.setItemClickedListener(new JACSOnRecyclerViewItemClicked)
