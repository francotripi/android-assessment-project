# Introduction
The branches in this repository are organized as follow:

**master**: This branch contains the original assessment project as it is in https://github.com/vente-privee/android-assessment-project 

**development**: This branch contains the final solution (challenges 1-6 and bonus activities 1 & 2):

https://github.com/francotripi/android-assessment-project/compare/master...development?expand=1


Then, you can also find the resolution of each one of the exercises in the following branches:

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
1. The Wrong State - *we took care of fetching the data remotely from the api. So the app is supposed to show a list of posters, but instead it still displays the progress bar. Can you find where the problem is and fix it? And to make sure we won't make the same mistake twice write a simple unit test.*

The problem is that the **ListViewModel** is not notifying and passing the data to the ListFragment when receiving the result from the web service.
A unit test was added to verify that ListView Model is notifying and sending the data when successfully recives the result from the service.

**Solution:** https://github.com/francotripi/android-assessment-project/compare/master...1-the_wrong_state?expand=1


2. The Lost Event - *when the user clicks on an item from the movies list, the app is supposed to display some information about the selected movie (this feature is located in the `detail` module). Currently the app doesn't respond to clicks, can you please fix it?*

**Solution:** https://github.com/francotripi/android-assessment-project/compare/1-the_wrong_state...2-the_lost_event?expand=1


3. The Lost State - *the app comes with a search bar to help users find their favorite movies. Unfortunately, there is a bug. When we rotate the screen, the app clears the text we just typed. Can you provide a solution to prevent this state loss from happening on rotation.*

**Solution:** https://github.com/francotripi/android-assessment-project/compare/2-the_lost_event...3-the_lost_state?expand=1


4. Some refreshments - *we made sure that this app handles networking errors. But we didn't implement any mechanism to reload the data, without quitting the app. Can you provide a way of refreshing the list of movies?*

**Solution:** https://github.com/francotripi/android-assessment-project/compare/3-the_lost_state...4-some_refreshments?expand=1


5. The chosen ones - *the favorites screen should show a list of the user's favorite movies. Try to implement this feature. Remember that the list of favorite movies should be available even after killing the app.*

**Solution:** https://github.com/francotripi/android-assessment-project/compare/4-some_refreshments...5-the_chosen_ones?expand=1


6. The Shrink - *first start by obfuscating the application using Proguard. Now you should have an empty details view in the app, your mission is to fix these issues. Now the apk is smaller, but we know it can be even smaller, use the apk analyzer to find out how to do so.*

**Solution:** https://github.com/francotripi/android-assessment-project/compare/5-the_chosen_ones...6-the_shrink?expand=1


### Bonus:
1. Memory leaks - *There is a memory leak. Try to find it and fix it.*

**Solution 1:** https://github.com/francotripi/android-assessment-project/compare/6-the_shrink...b-1-memory_leaks_solution_1?expand=1


**Solution 2:** https://github.com/francotripi/android-assessment-project/compare/6-the_shrink...b-1-memory_leaks_solution_2?expand=1


2. Java to Kotlin conversion - *convert `list` module from Java to Kotlin.*

**Solution:** https://github.com/francotripi/android-assessment-project/compare/6-the_shrink...b-2-java_to_kotlin_list_module?expand=1


3. List loading indicator - The app loads gradually the list of movies. Add a progress bar to indicate that the next page is loading.

**TODO**


