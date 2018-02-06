### Build Status ###
[![Build Status](https://travis-ci.org/Greenvillians281/Entech281_2018.svg?branch=master)](https://travis-ci.org/Greenvillians281/Entech281_2018)

### FAQ for UNIX lang in bash/terminal ###

_Instructions imply you're already in the `Team281_2018` directory_

### To push your changes to a branch ###
1. cd checkout `branch you want to push to (not master)`
2. ./gradlew build
3. git add --all
4. git commit -m '_Enter message here_'
5. git push

### To make a merge (pull) request ###
1. Follow all the steps in the section above this
2. Go to Pull Requests in GitHub
3. Click on New Pull Request
4. Select "base: master" and "compare:_YourBranchGoesHere_"
5. Write a __descriptive title and description__ (in description also explain what review you want made of your code)
6. Add people to review your code
7. Create the Pull Request

### To deploy to robot ###
1. Have the code you want to test _on your computer already_ (either by writing it locally or pulling it from a branch)
2. Connect to robot's WiFi hotspot 
3. ./gradlew deploy
4. Restart Robot Code in the Driver's Station

#### Helpful commands ####
* pwd (to find current directory)
* ls (to see the names of all files inside current directory)
* git reset --hard _CommitIdentifierCode_ (to rollback to a previous commit)
* git reset --hard (to reset to the last _git pull_'d version of a branch)
* git branch _BranchName_ (to create a branch)

### Eclipse help ###
To format the document to Team281 Conventions:
* Right click anywhere in the document, file or folder in the Package Explorer > Source > Format (either in a document, or on a folder to mass format contents)
To organize imports:
* Right click anywhere in the document, file or folder in the Package Explorer > Source > Organize Imports (either in a document, or on a folder to mass organize imports)
To run a test:
* Right click file or folder in the Package Explorer > Run As > JUnit Test

##### Last updated 2/6/18 by Santiago #####
