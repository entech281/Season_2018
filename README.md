### Build Status ###
[![Build Status](https://travis-ci.org/entech281/Season_2018.svg?branch=master)](https://travis-ci.org/entech281/Season_2018)


### FAQ for UNIX lang in bash/terminal ###

_Instructions imply you're already in the `entech281_2018` directory_

### To pull most recent version of code ###
1. git checkout `master`
2. git pull
3. Refresh folder in Eclipse if needed

### To push your changes to a branch ###
1. cd checkout `branch you want to push to (NOT master)`
2. ./gradlew build
3. git add --all
4. git commit -m '_Enter message here_'
5. git push

### To make a merge (pull) request ###
1. Follow all the steps in the section above this
2. Go to Pull Requests in the `entech2018_281` BitBucket sidebar
3. Click on Create Pull Request
4. Select branch you want merged and which branch you want to merge it into
5. Write a __descriptive title and description__ (in description also explain what review you want made of your code)
6. Create the Pull Request
7. Wait for "buddy coders" to review and accept the merge (pull) request

### To deploy to robot ###
1. Have the code you want to test _on your computer already_ (either by writing it locally or pulling it from a branch)
2. Connect to robot's WiFi hotspot 
3. ./gradlew deploy
4. Restart Robot Code in the Driver's Station

#### Helpful commands ####
* pwd (to find current directory)
* ls (to see the names of all files inside current directory)
* git reset --hard _commit identifier code_ (to rollback to a previous commit)
* git reset --hard (to reset to the last _git pull_'d version of a branch)

##### Last updated 2/1/18 by Santiago #####
