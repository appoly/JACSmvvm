# JACS MVVM
 A library providing a base Fragment, ViewModel and various tools to make creating a project with MVVM and data binding easier. This has been designed for one-activity apps.
 
 # Adding New Fragments
 When you add a new fragment to the project, have it extend JACSFragment or create your own BaseFragment that extends JACSFragment and have your new fragment extend your BaseFragment. 
 In the onCrease method:
 - Set 'layoutID' to the layout resource you want to use for the fragment
 - If you need to set up your views, override 'setUpViews()' and put your view set-up code there
 
 # Enabling ViewModels and Data Binding in Fragments
 - Extend the Fragment with 'JACSFragment<FragmentNameBinding, FragmentNameViewModel>'
 - Inside the onCreate method do 'viewModelClass = FragmentNameViewModel.class'
 - Override the method 'setUpViewModel'
 - Inside this method do 'viewBinding.setDataBindingNameViewModel(viewModel)'
 - If needed, override 'init()' and add setup code there
 
 The JACSFragment will handle creating the ViewModel of the given class and set up the data binding for your view. You will now be able to access your ViewModel with the variable 'viewModel'.
 
 # Creating A ViewModel
 - Extend the class with JACSViewModel or create your own BaseViewModel that extends JACSViewModel and extend new ViewModels by BaseViewModel
 - Create the public contructor to match the super
 - Id needed, overrive 'init()' and add setup code there
 
# Navigation
To call a Navigation action from a ViewModel that extends JACSViewModel:
- Ensure the fragment that's using the ViewModel extend JACSFragment
- Inside the ViewModel create a function for navigation (e.g public void goToSecondFragment())
- Handle any logic you need first, then call 'performAction(Integer, Bundle)' passing the integer (R.id.action_firstFragment_to_secondFragment) and either a Bundle or null
- If you passed a bundle, you can get the contents of the bundle inside the new fragment with 'getBundleData()'
