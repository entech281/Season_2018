<<<<<<< HEAD
##FAQ for UNIX lang in bash/terminal##

_Instructions imply you're already in the `entech281_2018` directory_

### To pull most recent version of code ###
1. git checkout `master`
2. git pull
3. Refresh `entech2018_281` folder in Eclipse

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
pwd (to find current directory)

##### Last updated 1/27/18 by Santiago #####

=======
# Contents
- [Build Status](#build-status)
- [Java Conventions](#java-conventions)
- [Terminal Help](#terminal-help)
- [Eclipse Help](#eclipse-help)
- [Helpful Information](#helpful-information)
- [External Links](#external-links)

## Build Status
[![Build Status](https://travis-ci.org/entech281/Season_2018.svg?branch=master)](https://travis-ci.org/entech281/Season_2018)
     
## Java Conventions
Eclipse Preferences > Java > Code Style > Formatter > New > Name the profile to Team281 Conventions > Initialize settings with "Java Conventions" > OK > Change Tab policy to "Spaces only" > Set Indentation size to 4 > Set Tab size to 4 > OK

## Terminal Help

_Instructions imply you're already in the `Team281_2018` directory_

### To push your changes to a branch
1. git checkout _branchname_
2. git add --all
3. git commit -m '_Enter commit message here_'
4. git push

### To make a merge (pull) request
1. Go to Pull Requests in GitHub
2. Click on New Pull Request
3. Select "base: master" and "compare: _Your Branch Goes Here_"
4. Make a good title and description so reviewers understand your code
5. Add people to review your code

### To deploy to robot
1. Connect to robot's WiFi hotspot 
2. ./gradlew deploy
3. Restart Robot Code in the Driver's Station

## Eclipse Help

>Eclipse has lost its mind! - Dave Cowden

### Fix Eclipse 101 
1. cd (directory you want to be in)
2. ./gradlew clean
3. ls -la
4. rm -rf .classpath .project .settings 
5. ./gradlew eclipse
6. Open eclipse and browse to directory

### To format your document
![Gif showing process](https://media.giphy.com/media/26DN2K5FX5W8GD4mQ/giphy.gif)

### To run a test
![Gif showing process](https://media.giphy.com/media/l4pT1dvQZCEPEJBIc/giphy.gif)

## Helpful Information 
* pwd (to find current directory)
* ls (to see the names of all files inside current directory)
* git reset --hard _CommitIdentifierCode_ (to rollback to a previous commit)
* git reset --hard (to reset to the last _git pull_'ed version of a branch)
* git reset -f (to unto a branch checkout)
* git checkout -b _BranchName_ (to create a branch)

## External Links
- [Understanding PIDF and gains](https://github.com/entech281/Season_2018/wiki/Understanding-PIDF-and-gains)

<sub><sup>[Back to top](#contents)</sup></sub>

##### Last updated 2/10/18 by Santiago #####
>>>>>>> master
