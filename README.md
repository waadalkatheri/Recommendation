# RecommendationSystem-firebase
Algorithm produces a list of the recommended parks for each user, it takes the list of parks from the database and the current user login type (guest/registered user) as parameters. The algorithm works as follows:
             Case 1: if the user has records in the visit history, we get the facilities of each visited park and keep a counter of each facility in order to determine its frequency. Using each facility collected from the visited parks list, we will create a list of the preferred facilities by adding only facilities with frequencies equal to or greater than 3
             Case 2: if the user has no records, we will generate the list of the user’s preferred facilities using the user’s preferences in the user’s profile.
             1.2 Create a park preference counter for each park and compare the preferred facilities list with the facilities list of all parks, if a match is found for a facility in the park’s list of facilities, increment the park’s preference counter
              1.3  Now that we have a list of parks with their preference counter, sort the list of parks according to the preference counter, having parks of the highest preference counter at the top of the list.