# JACS MVVM
A library providing a base Fragment, ViewModel and various tools to make creating a project with MVVM and data binding easier. This has been designed for one-activity apps.

## Gradle
To add this library to your project, add the following to your gradle file:
```java
dependencies {
    implementation 'com.github.appoly:JACSmvvm:v0.2.0'
}
```

## Included Animations 
Within this library are 6 animations. These are sliding and fading animations which can be seen below:
### Sliding 
![SlidingAnimations](https://github.com/appoly/JACSmvvm/blob/master/app/src/main/res/raw/sliding_anim_video.gif)
### Fading
![FadingAnimations](https://github.com/appoly/JACSmvvm/blob/master/app/src/main/res/raw/fade_anim_video.gif)

## Adding New Fragments
When you add a new fragment to the project, have it extend JACSBaseFragment or create your own BaseFragment that extends JACSBaseFragment and have your new fragment extend your BaseFragment. 
In the onCreate method:
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
        
       // Must set the class for this view's ViewModel
       viewModelClass = FragmentExampleViewModel.class;
   }

   @Override
   protected void setUpViewModel() {
       super.setUpViewModel();
       // Binding is automatically cast to the correct type
       // viewModel is also automatically cast to the correct type
       viewBinding.setExampleViewModel(viewModel);
        
   }
   
   @Override
   protected void setupViews() {
        
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
- Handle any logic you need first, then call 'performAction(Integer, Bundle)' passing the integer (R.id.action_firstFragment_to_secondFragment) and either a Bundle or null
- If you passed a bundle, you can get the contents of the bundle inside the new fragment with 'getBundleData()'

# Recycler Views
To use the JACSRecyclerAdapter you will need to do the following:
- Ensure that your recyclerView adapter extends from JACSRecyclerViewAdapter. To do this you will need to add 2 parameters into the crocodile parentheses '<>'. You will need to add the data type which the recylerView will be using, and the Cell that the recyclerView will be displaying (e.g <String, ExampleCell>).
- To setup the cell you will need to create a class that extends from JACSViewHolder. With this you will only need to add 1 parameter into the crocodile parentheses '<>' which is the data type for the recyclerViews data, in this case it would be `String`
- You can now see that the ExampleCell class required you to override an `onBind` method, use this to set the data for the cells (e.g textView.setText(item)). 
- You will also have had to override 2 methods for the recyclerView adapter. These are the `setData` and `onCreateViewHolder` methods. The setData method is used to dataBind the data that this recyclerView will be displaying, therefore you should set the data within the base adapter to these new mData (e.g this.mData = data) also any filtering of the data can be perfomed here. The onCreateViewHolder method is used for you to inflate you cell with the correct layout id (e.g ExampleCell(inflate(R.layout.cell_example, parent, false));). You can also use this method to set any custom behavour for your cell
- Finally to tie it all together, you just attatch your adapter to your recyclerView, then bind the data to the recyclerView in xml (by using the app:data="") and, if needed, add the onClick listener by calling 'adapter.setItemClickedListener(new JACSOnRecyclerViewItemClicked'
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
