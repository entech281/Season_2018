# README #

This README would normally document whatever steps are necessary to get your application up and running.

### FAQ for UNIX ###

### To pull most recent version of code ###
* Open bash/terminal
* cd entech2018_281
* git checkout master
* git pull
* cd yertle2018
* ./gradlew build
* ./gradlew eclipse
* Right click on the entech2018_281 folder in eclipse and click "Refresh"

### To push your changes to github ###
* cd checkout name-of-branch-you-want-to-push-to
* git add --all
* git commit -m 'Enter message here'
* git push

### To make a new set of changes ###
* git pull  ( make sure you have latest stuff)
* git checkout -b <your_initials>_name_for_your_changes
* cd Yertle2018
* code all the things
* ./gradlew build ( make sure your changes build )
* git push -u origin <your_initials>_name_for_your_changes
* go to bitbutcket.org and make a new merge request
* get someone else to approve it and merge it to master

### To deploy to robot (ie. yertle) ###
* Have the code on your computer you want to test (see 
* Switch to robot's internet hotspot (ie. 281_yertle)
* ./gradlew deploy

### Last updated 1/27/18 by Santiago G ###