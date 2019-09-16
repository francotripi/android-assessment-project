# Introduction
The branches in this repository are organized as follow:

***master***: This branch contains the original assessment project as it is in https://github.com/vente-privee/android-assessment-project 

***development***: This branch contains the final solution (challenges 1-6 and bonus activities 1 & 2):

https://github.com/francotripi/android-assessment-project/compare/master...development?expand=1


You can also find the resolution of each of the exercises in the following branches:

### Challenges
1. *1-the_wrong_state*
2. *2-the_lost_event*
3. *3-the_lost_state*
4. *4-some_refreshments*
5. *5-the_chosen_ones*
6. *6-the_shrink*

### Bonus
* *b-1-memory_leaks_solution_1*
* *b-1-memory_leaks_solution_2*
* *b-2-java_to_kotlin_list_module*


# Challenges
### 1. The Wrong State 
###### *We took care of fetching the data remotely from the api. So the app is supposed to show a list of posters, but instead it still displays the progress bar. Can you find where the problem is and fix it? And to make sure we won't make the same mistake twice write a simple unit test.*

The problem is that the **ListViewModel** is not notifying and passing the data to the ListFragment when receiving the result from the web service.

A unit test was added to verify that ListView Model is notifying and sending the data when successfully recives the result from the service.

**Solution:** https://github.com/francotripi/android-assessment-project/compare/master...1-the_wrong_state?expand=1

------------------

### 2. The Lost Event 
###### *When the user clicks on an item from the movies list, the app is supposed to display some information about the selected movie (this feature is located in the `detail` module). Currently the app doesn't respond to clicks, can you please fix it?*

The problem is that the **onItemClick** event is not implemented. This was solved doing an Intent to DetailActivity and passing the movie id throw it.

**Solution:** https://github.com/francotripi/android-assessment-project/compare/1-the_wrong_state...2-the_lost_event?expand=1

------------------

### 3. The Lost State
###### *The app comes with a search bar to help users find their favorite movies. Unfortunately, there is a bug. When we rotate the screen, the app clears the text we just typed. Can you provide a solution to prevent this state loss from happening on rotation.*

The problem here is that the activity is recreated after each rotation by default. This can be solved saving the data using **onSaveInstanceState** method.

**Solution:** https://github.com/francotripi/android-assessment-project/compare/2-the_lost_event...3-the_lost_state?expand=1

------------------

### 4. Some refreshments
###### *We made sure that this app handles networking errors. But we didn't implement any mechanism to reload the data, without quitting the app. Can you provide a way of refreshing the list of movies?*

This was solved by adding a floating button in ListActivity which, by touching it, deletes the data stored in the cache and calls the web service to obtain the data again.

**Solution:** https://github.com/francotripi/android-assessment-project/compare/3-the_lost_state...4-some_refreshments?expand=1

------------------

### 5. The chosen ones 
###### *The favorites screen should show a list of the user's favorite movies. Try to implement this feature. Remember that the list of favorite movies should be available even after killing the app.*

The strategy used to implement this feature was to save the movie id using some persistence mechanism and then use API to obtain them from the web service. 

Despite we only need to save a list of String (movies's id), I decided to use **Room Library**,following the Android Architecture Components recommendation, to persist the data in a local database. This was implemented in a separate module named **persisntence** that is used by **:feature:favorite** to obtain the saved movie ids and **:feature:detail** to save the movie id when tapping on the star button from the menu *(NOTE: this star button doesn't change the state when is tapped)*.

*NOTE: For code reuse and faster implementation, I added a dependency between **:feature:list** and **:feature:favorite** modules, where Favorite use some clases from List module. This is a bad practice that can be solved by moving the common classes to a separate module and making both modules dependent on the new one.*


**Solution:** https://github.com/francotripi/android-assessment-project/compare/4-some_refreshments...5-the_chosen_ones?expand=1

------------------

### 6. The Shrink 
###### *First start by obfuscating the application using Proguard. Now you should have an empty details view in the app, your mission is to fix these issues. Now the apk is smaller, but we know it can be even smaller, use the apk analyzer to find out how to do so.*

The application can be obfuscated changing the BuildVariant to **debugProguard** because the Proguard is already configured for that build type.
When enabling Proguard the app stops working properly because the classes and class members names are changed and this breaks the start activity Intent between *ListActivity* and *DetailActivity*.
This was fixed adding a rule in the app Proguard.

Analyzing the apk size I found that there is a heavy .jpg image that can be size reduced being converting and compressing to .webp using Android Studio tool for that. *(NOTE: This image is not  used in the app, it could be removed too)*.

**Solution:** https://github.com/francotripi/android-assessment-project/compare/5-the_chosen_ones...6-the_shrink?expand=1

------------------

## Bonus:
### 1. Memory leaks 
###### *There is a memory leak. Try to find it and fix it.*

The memory leak appears in DetailActivity because there is a reference to this Activity inside a companion object. When the Activity is destroyed the GC can not remove it from the heap memory because there is still a reference to it from this variable declared inside the companion object. I consider two different alternatives to solve this issue:

1. Removing the reference inside the companion object that is used by DetailViewModel to get the movie id pulling it from the DetailActivity. I opted for the DetailActivity pushing the movie id to the DetailViewModel instead.

**Solution 1:** https://github.com/francotripi/android-assessment-project/compare/6-the_shrink...b-1-memory_leaks_solution_1?expand=1

2. Declaring the reference inside the companion object as WeakReference, so the GC can remove it from heap memory.

**Solution 2:** https://github.com/francotripi/android-assessment-project/compare/6-the_shrink...b-1-memory_leaks_solution_2?expand=1

The app was also inspected using LeakCanary (https://square.github.io/leakcanary/) to discard any other memory leak.

------------------

### 2. Java to Kotlin conversion 
###### *convert `list` module from Java to Kotlin.*

This process was done following this steps:
1. Rename each file from .java to .kt
2. Commit change
3. Rename each file from .kt to .java
4. Convert using Android Studio tool
5. Commit change

As recommended in the following article: https://medium.com/swlh/12-steps-to-convert-your-java-class-to-kotlin-the-right-way-9a718cfb498d

**Solution:** https://github.com/francotripi/android-assessment-project/compare/6-the_shrink...b-2-java_to_kotlin_list_module?expand=1

------------------

### 3. List loading indicator
###### The app loads gradually the list of movies. Add a progress bar to indicate that the next page is loading.

**NOT IMPLEMENTED**


