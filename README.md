# JACS MVVM
A library providing a base Fragment, ViewModel and various tools to make creating a project with MVVM and data binding easier. This has been designed for one-activity apps.

## Gradle
To add this library to your project, add the following to your gradle file:
```java
dependencies {
    implementation 'com.github.jamesstonedeveloper:JACSmvvm:v0.2.0'
}
```

 
## Adding New Fragments
When you add a new fragment to the project, have it extend JACSBaseFragment or create your own BaseFragment that extends JACSBaseFragment and have your new fragment extend your BaseFragment. 
In the onCrease method:
- Set 'layoutID' to the layout resource you want to use for the fragment
- If you need to set up your views, override `setUpViews()` and put your view set-up code there

### Fragment Example
```java
public class FragmentExample extends JACSBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutID = R.layout.example_layout;
    }

    @Override
    protected void setupViews() {
        
    }
}
```


 
## Enabling ViewModels and Data Binding in Fragments
 - Extend the Fragment with 'JACSBaseFragment<FragmentNameBinding, FragmentNameViewModel>'
 - Inside the onCreate method do 'viewModelClass = FragmentNameViewModel.class'
 - Override the method `setUpViewModel`
 - Inside this method do 'viewBinding.setDataBindingNameViewModel(viewModel)'
 - If needed, override `init` and add setup code there

 The JACSBaseFragment will handle creating the ViewModel of the given class and set up the data binding for your view. You will now be able to access your ViewModel with the variable 'viewModel'.
 
### Fragment With ViewModel Example
```java
public class FragmentExample extends JACSBaseFragment<FragmentExampleBinding, FragmentExampleViewModel> {

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       layoutID = R.layout.example_layout;
        
       // Must set the class for this views ViewModel
       viewModelClass = FragmentExampleViewModel.class;
   }

   @Override
   protected void setupViews() {
       // Binding is automatically cast to the correct type
       // viewModel is also automatically cast to the correct type
       viewBinding.setExampleViewModel(viewModel);
        
   }
 }
 ```
 
 
## Creating A ViewModel
 - Extend the class with JACSViewModel or create your own BaseViewModel that extends JACSViewModel and extend new ViewModels by BaseViewModel
 - Create the public contructor to match the super
 - Id needed, overrive `init` and add setup code there
 
### ViewModel Example
```java
public class FragmentExampleViewModel extends JACSViewModel {
    
   // Required constructor
   public FragmentExampleViewModel(@NonNull Application application) {
       super(application);
   }
    
}
```
 
 
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
 
### RecyclerView Adapter Example
```java
public class ExampleAdapter extends JACSRecyclerViewAdapter<String, ExampleCellBasic> {

   public ExampleAdapter(Context context) {
       super(context);
   }

   @Override
   public void setData(List<String> data) {
       this.items = data;
       notifyDataSetChanged();
   }

   @NonNull
   @Override
   public ExampleCellBasic onCreateViewHolder(ViewGroup parent, int viewType) {
       return new ExampleCellBasic(inflate(R.layout.cell_example, parent, false));
   }

}
```
 
### ViewHolder Example
```java
public class ExampleCellBasic extends JACSViewHolder<String> {

   public TextView textView;

   public ExampleCellBasic(@NonNull View itemView) {
       super(itemView);
       textView = itemView.findViewById(R.id.textView);
   }
    
   @Override
   public void onBind(final String item, @Nullable final JACSOnRecyclerViewItemClicked<String> itemClickedListener) {
       textView.setText(item);

       itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (itemClickedListener != null) {
                   itemClickedListener.onItemClicked(position, item);
               }
           }
       });

   }
    
}
```
